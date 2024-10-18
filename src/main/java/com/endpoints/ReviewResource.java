package com.endpoints;

import com.persistence.AppUser;
import com.persistence.Hotel;
import com.persistence.Review;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("reviews")
public class ReviewResource extends PanacheEntity implements Resource<Review> {

    @Path("reviews")
    public Response findAllEntities() {
        List<Review> reviews = Review.listAll();
        if(reviews.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reviews).build();
    }

    @Override
    public Response find(Long filter) {
        AppUser user = AppUser.findById(filter);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @Transactional
    @Override
    public Response create(Review review) {
        try{
            review.hotel = HotelResource.findById(review.hotelId);
            review.appUser = AppUser.findById(review.userId);
            review.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Review review) {
        Review updateReview = Review.findById(id);

        if(updateReview == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Review not found.").build();
        }

        try{
            updateReview.hotel = Hotel.findById(review.hotelId);
            updateReview.message = review.message;
            updateReview.appUser = AppUser.findById(review.userId);
            updateReview.rating = review.rating;

            updateReview.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.ok(updateReview).build();
    }


    @Transactional
    @Override
    public Response delete(Long id) {
        return null;
    }
}

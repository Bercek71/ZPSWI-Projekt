package com.resources;

import com.persistence.AppUser;
import com.persistence.Hotel;
import com.persistence.Review;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("reviews")
public class ReviewResource implements Resource<Review> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<Review> reviews = Review.listAll();
        if(reviews.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"No review was found.\"}")
                    .build();
        }
        return Response.ok(reviews)
                .build();
    }

    @Override
    public Response find(Long filter) {
        Review review = Review.findById(filter);
        if (review == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"Review not found.\"}")
                    .build();
        }
        return Response.ok(review)
                .build();
    }

    @Transactional
    @Override
    public Response create(Review review) {
        try {
            review.hotel = Hotel.findById(review.hotelId);
            review.appUser = AppUser.findById(review.userId);
            review.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(review)
                .build();
    }

    @Transactional
    @Override
    public Response update(Long id, Review review) {
        Review updateReview = Review.findById(id);

        if (updateReview == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"Review not found.\"}")
                    .build();
        }

        try {
            updateReview.hotel = Hotel.findById(review.hotelId);
            updateReview.message = review.message;
            updateReview.appUser = AppUser.findById(review.userId);
            updateReview.rating = review.rating;

            updateReview.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.ok(updateReview)
                .build();
    }


    @Transactional
    @Override
    public Response delete(Long id) {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .entity("{\"msg\": \"Method not implemented.\"}")
                .build();
    }
}

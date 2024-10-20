package com.endpoints;

import com.persistence.AppUser;
import com.persistence.Booking;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("bookings")
public class BookingResource implements Resource<Booking> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
       List<Booking> bookings = Booking.listAll();
       if(bookings.isEmpty()) {
           return Response.status(Response.Status.NOT_FOUND).build();
       }
       return Response.ok(bookings).build();
    }

    @Override
    public Response find(Long filter) {
        Booking booking = Booking.findById(filter);
        if (booking == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(booking).build();
    }

    @Override
    public Response create(Booking booking) {
        try{
            booking.appUser = AppUser.findById(booking.userId);
            booking.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Booking booking) {

        Booking updateBooking = Booking.findById(id);

        if(updateBooking == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Booking not found.").build();
        }

        try{
            updateBooking.appUser = AppUser.findById(booking.userId);
            updateBooking.priceTotal = booking.priceTotal;
            updateBooking.persist();

        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.ok(updateBooking).build();
    }

    @Transactional
    @Override
    public Response delete(Long id) {
        return null;
    }
}

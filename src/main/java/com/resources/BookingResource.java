package com.resources;

import com.persistence.AppUser;
import com.persistence.Booking;
import com.persistence.Reservation;
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
        if (bookings.isEmpty()) {
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
    @Transactional
    public Response create(Booking booking) {
        try {
            if(booking == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Wrong body").build();
            }
            if(booking.reservations.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No reservations").build();
            }

            booking.appUser = AppUser.findById(booking.userId);
            if(booking.appUser == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("UserId doesn't exist.").build();
            }
            booking.persist();

            for (Reservation reservation : booking.reservations) {
                if (reservation != null) {
                    if(reservation.startDate.isAfter(reservation.endDate)){
                        return Response.status(Response.Status.BAD_REQUEST).entity("Start date later than end date.").build();
                    }
                    reservation.booking = booking;
                    reservation.persist();
                }
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Booking booking) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @Transactional
    @Override
    public Response delete(Long id) {
        return null;
    }
}

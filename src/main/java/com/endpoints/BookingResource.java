package com.endpoints;

import com.persistence.Booking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/booking")
public class BookingResource {

    @Inject


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Booking> getBookings() {
        return Booking.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Booking addBooking(Booking booking) {
        booking.persist();
        return booking;
    }

}

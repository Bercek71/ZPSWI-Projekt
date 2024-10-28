package com.resources;

import com.persistence.AppUser;
import com.persistence.Booking;
import com.persistence.Reservation;
import com.persistence.Room;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("reservations")
public class ReservationResource implements Resource<Reservation> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<Reservation> reservations = Reservation.listAll();
        if (reservations.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reservations).build();
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
    public Response create(Reservation reservation) {
        try {
            if(reservation == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Wrong body").build();
            }
            reservation.booking = Booking.findById(reservation.bookingId);
            reservation.room = Room.findById(reservation.roomId);
            reservation.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Reservation reservation) {
        Reservation updateReservation = Reservation.findById(id);

        if (updateReservation == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Reservation not found.").build();
        }

        try {
            updateReservation.startDate = reservation.startDate;
            updateReservation.endDate = reservation.endDate;
            updateReservation.room = Room.findById(reservation.roomId);
            updateReservation.status = reservation.status;
            updateReservation.price = reservation.price;
            updateReservation.paidAt = reservation.paidAt;
            updateReservation.booking = Booking.findById(reservation.bookingId);

            updateReservation.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.ok(updateReservation).build();
    }

    @Transactional
    @Override
    public Response delete(Long id) {
        return null;
    }
}

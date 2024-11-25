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

/**
 * RESTful resource class for managing bookings.
 * Provides endpoints to retrieve, create, update, and delete bookings.
 */
@Path("bookings")
public class BookingResource implements Resource<Booking> {

    /**
     * Retrieves all booking entities.
     *
     * @return A Response containing a list of all bookings in JSON format.
     *         Returns 404 if no bookings are found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<Booking> bookings = Booking.listAll();
        if (bookings.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{'msg': 'No booking was found.'}")
                    .build();
        }
        return Response.ok(bookings).build();
    }

    /**
     * Finds a specific booking by its ID.
     *
     * @param filter The ID of the booking to retrieve.
     * @return A Response containing the booking if found, or a 404 status with an error message if not.
     */
    @Override
    public Response find(Long filter) {
        Booking booking = Booking.findById(filter);
        if (booking == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{'msg': 'Booking not found.'}")
                    .build();
        }
        return Response.ok(booking).build();
    }

    /**
     * Creates a new booking.
     *
     * @param booking The Booking object to create. The object must include reservations and a valid userId.
     * @return A Response containing the created Booking object and a 201 status if successful.
     *         Returns 400 if the request body is invalid or reservations are missing.
     *         Returns 500 if the associated userId does not exist or an error occurs during persistence.
     */
    @Override
    @Transactional
    public Response create(Booking booking) {
        try {
            if(booking == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{'msg': 'Wrong body'}")
                        .build();
            }
            if(booking.reservations.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{'msg': 'No reservations.'}")
                        .build();
            }

            booking.appUser = AppUser.findById(booking.userId);
            if(booking.appUser == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{'msg': 'UserId doesn't exist.'}")
                        .build();
            }
            booking.persist();

            for (Reservation reservation : booking.reservations) {
                if (reservation != null) {
                    if(reservation.startDate.isAfter(reservation.endDate)){
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity("{'msg': 'Start date later than end date.'}")
                                .build();
                    }
                    reservation.booking = booking;
                    reservation.persist();
                }
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{'msg': '" + e.getMessage() + "'}")
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(booking)
                .build();
    }

    /**
     * Updates an existing booking by its ID.
     *
     * @param id The ID of the booking to update.
     * @param booking The updated Booking object.
     * @return A Response with a 501 status indicating that the method is not implemented.
     */
    @Transactional
    @Override
    public Response update(Long id, Booking booking) {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .entity("{'msg': 'Method not implemented.'}")
                .build();
    }

    /**
     * Deletes a booking by its ID.
     *
     * @param id The ID of the booking to delete.
     * @return A Response with a 501 status indicating that the method is not implemented.
     */
    @Transactional
    @Override
    public Response delete(Long id) {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .entity("{'msg': 'Method not implemented.'}")
                .build();
    }
}

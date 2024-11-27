package com.resources;

import com.persistence.AppUser;
import com.persistence.Booking;
import com.persistence.Reservation;
import io.quarkus.security.Authenticated;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * RESTful resource class for managing bookings.
 * Provides endpoints to retrieve, create, update, and delete bookings.
 */
@Path("bookings")
public class BookingResource implements Resource<Booking> {

    @Inject
    JsonWebToken jwt;


    /**
     * Retrieves all booking entities.
     *
     * @return A Response containing a list of all bookings in JSON format.
     *         Returns 404 if no bookings are found.
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response findAllEntities() {
        List<Booking> bookings = Booking.listAll();
        if (bookings.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"No booking was found.\"}")
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
    @Authenticated
    public Response find(Long filter) {
        Booking booking = Booking.findById(filter);
        if (booking == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"Booking not found.\"}")
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
    @Authenticated
    public Response create(Booking booking) {
        try {
            if(booking == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"msg\": \"Wrong body\"}")
                        .build();
            }
            if(booking.reservations.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"msg\": \"No reservations.\"}")
                        .build();
            }

            Optional<AppUser> user = AppUser.find("email", jwt.getSubject()).singleResultOptional();
            if(user.isEmpty()) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{msg: 'UserId doesn't exist.'}").build();

            booking.appUser = AppUser.findById(booking.userId);
            if(booking.appUser == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"msg\": \"UserId doesn\"t exist.\"}")
                        .build();

            }
            booking.appUser = user.get();
            for (Reservation reservation : booking.reservations) {
                if (reservation != null) {
                    if(reservation.startDate.isAfter(reservation.endDate)){
                        return Response.status(Response.Status.BAD_REQUEST).entity("{msg: 'Start date later than end date.'}").build();
                    }
                    reservation.booking = booking;
                    reservation.status = Reservation.ReservationStatus.PENDING;
                    reservation.room = Room.findById(reservation.roomId);
                    if (!reservation.room.isReservable(reservation.startDate, reservation.endDate)) {
                        return Response.status(Response.Status.BAD_REQUEST).entity("{msg: 'Room is not reservable.'}").build();
                    }
                    reservation.price = Period.between(reservation.startDate, reservation.endDate).getDays() * reservation.room.pricePerNight;
                    booking.priceTotal += reservation.price;
                }
            }
            booking.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{msg: '" + e.getMessage() + "'}").build();
        }
        return Response.status(Response.Status.CREATED).entity(booking).build();
    }

    @POST
    @Path("unregistered")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUnregistered(UnregisteredBooking unregisteredBooking) {
        try {
            Booking booking = unregisteredBooking.booking;
            if(booking == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{msg: 'Wrong body'}").build();
            }
            if(booking.reservations.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{msg: 'No reservations.'}").build();
            }

            AppUser blankUser = AppUser.find("email", unregisteredBooking.email).firstResult();
            if (blankUser == null){
                blankUser = new AppUser();
                blankUser.email = unregisteredBooking.email;
                blankUser.persist();
            }

            blankUser = AppUser.find("email", blankUser.email).firstResult();
            if(blankUser == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{msg: 'Ups.'}").build();
            }
            booking.appUser = blankUser;

            for (Reservation reservation : booking.reservations) {
                if (reservation != null) {
                    if(reservation.startDate.isAfter(reservation.endDate)){
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity("{\"msg\": \"Start date later than end date.\"}")
                                .build();
                    }
                    reservation.booking = booking;
                    reservation.status = Reservation.ReservationStatus.PENDING;
                    reservation.room = Room.findById(reservation.roomId);
                    if (!reservation.room.isReservable(reservation.startDate, reservation.endDate)) {
                        return Response.status(Response.Status.BAD_REQUEST).entity("{msg: 'Room is not reservable.'}").build();
                    }
                    reservation.price = Period.between(reservation.startDate, reservation.endDate).getDays() * reservation.room.pricePerNight;
                    booking.priceTotal += reservation.price;
                }
            }
            booking.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.status(Response.Status.CREATED).entity(unregisteredBooking).build();

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
                .entity("{\"msg\": \"Method not implemented.\"}")
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
                .entity("{\"msg\": \"Method not implemented.\"}")
                .build();
    }
}

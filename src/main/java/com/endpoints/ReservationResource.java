package com.endpoints;

import com.persistence.AppUser;
import com.persistence.Booking;
import com.persistence.Reservation;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class ReservationResource extends PanacheEntity implements Resource<Reservation> {
    @Override
    public Response findAllEntities() {
        List<Reservation> reservations = Reservation.listAll();
        if(reservations.isEmpty()) {
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

    @Override
    public Response create(PanacheEntity entity) {
        try{
            entity.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Override
    public Response update(PanacheEntity entity) {
        return null;
    }

    @Override
    public Response delete(Long id) {
        return null;
    }
}
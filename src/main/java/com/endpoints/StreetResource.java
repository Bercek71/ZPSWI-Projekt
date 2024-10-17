package com.endpoints;

import com.persistence.AppUser;
import com.persistence.Booking;
import com.persistence.Street;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class StreetResource extends PanacheEntity implements Resource<Street> {

    @Override
    public Response findAllEntities() {
        List<Street> streets = Street.listAll();
        if(streets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(streets).build();
    }

    @Override
    public Response find(Long filter) {
        Street street = Street.findById(filter);
        if (street == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(street).build();
    }

    @Override
    public Response create(PanacheEntity entity) {
        return null;
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

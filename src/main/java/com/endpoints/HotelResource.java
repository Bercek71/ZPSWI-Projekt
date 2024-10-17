package com.endpoints;

import com.persistence.Booking;
import com.persistence.Hotel;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class HotelResource extends PanacheEntity implements Resource<Hotel> {
    @Override
    public Response findAllEntities() {
        List<Hotel> hotels = Hotel.listAll();
        if(hotels.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(hotels).build();
    }

    @Override
    public Response find(Long filter) {
        Hotel hotel = Hotel.findById(filter);
        if (hotel == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(hotel).build();
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

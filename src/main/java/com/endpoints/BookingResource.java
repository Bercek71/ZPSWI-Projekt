package com.endpoints;

import com.persistence.Booking;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("bookings")
public class BookingResource extends PanacheEntity implements Resource<Booking> {

    @Override
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

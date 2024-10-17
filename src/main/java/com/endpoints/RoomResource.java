package com.endpoints;

import com.persistence.AppUser;
import com.persistence.Booking;
import com.persistence.Room;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class RoomResource  extends PanacheEntity implements Resource<Room> {

    @Override
    public Response findAllEntities() {
        List<Room> rooms = Room.listAll();
        if(rooms.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(rooms).build();
    }

    @Override
    public Response find(Long filter) {
        Room room = Room.findById(filter);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(room).build();
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

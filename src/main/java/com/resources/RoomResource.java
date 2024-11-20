package com.resources;

import com.persistence.Amenity;
import com.persistence.Hotel;
import com.persistence.Room;
import com.persistence.RoomAmenity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("rooms")
public class RoomResource implements Resource<Room> {

    @GET
    @Path("{id}/amenities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAmenities(@PathParam("id") Long roomId) {
        List<Amenity> amenities = RoomAmenity.findAllAmenitiesForRoom(roomId);

        if(amenities == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{msg: 'No amenity was found.'}").build();
        }
        return Response.ok(amenities).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<Room> rooms = Room.listAll();
        if (rooms.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("{msg: 'No room was found.'}").build();
        }
        return Response.ok(rooms).build();
    }

    @Override
    public Response find(Long filter) {
        Room room = Room.findById(filter);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{msg: 'Room not found.'}").build();
        }
        return Response.ok(room).build();
    }

    @Transactional
    @Override
    public Response create(Room room) {
        try {
            room.hotel = Hotel.findById(room.hotelId);
            room.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{msg: '" + e.getMessage() + "'}").build();
        }
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Room room) {
        Room updateRoom = Room.findById(id);

        if (updateRoom == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{msg: 'Room not found.'}").build();
        }

        try {
            updateRoom.roomNumber = room.roomNumber;
            updateRoom.type = room.type;
            updateRoom.pricePerNight = room.pricePerNight;
            updateRoom.isAvailable = room.isAvailable;
            updateRoom.hotel = Hotel.findById(room.hotelId);

            updateRoom.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{msg: '" + e.getMessage() + "'}").build();
        }
        return Response.ok(updateRoom).build();
    }


    @Transactional
    @Override
    public Response delete(Long id) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).entity("{msg: 'Function not implemented.'}").build();
    }
}

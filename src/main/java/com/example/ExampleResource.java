package com.example;

import com.persistence.ExampleRoom;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


//Ukázka použití JPA entity a transakce v REST endpointu
@Path("/rooms")
public class ExampleResource {
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        try{
            List<ExampleRoom> exampleRooms = ExampleRoom.listAll();
            return Response.ok(exampleRooms).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("id") Long id) {
        try {
            ExampleRoom exampleRoom = ExampleRoom.findById(id);
            if (exampleRoom == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(exampleRoom).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

package com.endpoints;

import com.persistence.Booking;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.InvocationTargetException;

@Path("/test")
public class TestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response Test() {
        return Response.ok(Booking.listAll()).build();
    }
}

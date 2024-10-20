package com.endpoints;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public interface Resource<T extends PanacheEntity> {

    //Long for now, might use filter
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response find(@PathParam("id") Long filter);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(T entity);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response update(@PathParam("id") Long id, T entity);

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response delete(@PathParam("id") Long id);
}

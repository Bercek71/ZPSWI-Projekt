package com.resources;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public interface Resource<T extends PanacheEntity> {

    // format for date parsing
    //TODO: Might need to change this
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);


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

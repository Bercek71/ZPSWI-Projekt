package com.endpoints;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public interface Resource<T extends PanacheEntity> {

    @GET
    @Path("findAll")
    @Produces(MediaType.APPLICATION_JSON)
    Response findAllEntities();

    //Long for now, might use filter
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response find(@PathParam("id") Long filter);

    @POST
    @Path("/create")
    @Transactional
    Response create(PanacheEntity entity);

    @PUT
    @Path("/update/")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response update(PanacheEntity entity);

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    Response delete(@PathParam("id") Long id);
}

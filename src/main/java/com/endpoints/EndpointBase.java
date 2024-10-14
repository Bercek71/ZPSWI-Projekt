package com.endpoints;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("{entity}s")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EndpointBase {

    @Inject
    EndpointServiceLayer service;

    //Generic function for fetching all table rows
    @GET
    @Transactional
    public Response getAll(@PathParam("entity") String entityName) {
        try {
            //Match class by entity param, call service method to find all entities
            Class<? extends PanacheEntityBase> entityClass = getEntityClass(entityName);
            List<?> entities = service.listAll(entityClass);

            return Response.ok(entities).build();
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }
    }

    //Generic function for searching by id
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("entity") String entityName, @PathParam("id") Long id) {
        try {
            //Match class by entity param, call service method to find entity by id
            Class<? extends PanacheEntityBase> entityClass = getEntityClass(entityName);
            PanacheEntityBase entity = service.findById(entityClass, id);

            if (entity == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Entity with ID " + id + " not found").build();
            }

            return Response.ok(entity).build();
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found: " + entityName).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    //TODO Fix assigning foreign key id's correctly, change pathing
    @Path("/create")
    @POST
    @Transactional
    public Response create(@PathParam("entity") String entityName, String requestBody) {
        try {
            //Match class by entity param
            Class<? extends PanacheEntityBase> entityClass = getEntityClass(entityName);

            //Deserialization
            Jsonb jsonb = JsonbBuilder.create();
            PanacheEntityBase entity = jsonb.fromJson(requestBody, entityClass);

            entity.persist();

            return Response.status(Response.Status.CREATED).entity(entity).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create entity: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("entity") String entityName, @PathParam("id") Long id) {
        try {
            //Match class by entity param, call service method to delete row in DB by id
            Class<? extends PanacheEntityBase> entityClass = getEntityClass(entityName);
            boolean deleted = service.delete(entityClass, id);
            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND).entity("Entity with ID " + id + " not found").build();
            }
            return Response.noContent().build();
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found: " + entityName).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    //TODO UPDATE
    //
    //
    //
    //

    //HELPER FUNCTIONS
    private Class<? extends PanacheEntityBase> getEntityClass(String entityName) throws ClassNotFoundException {
        String className = "com.persistence." + capitalize(entityName);
        return (Class<? extends PanacheEntityBase>) Class.forName(className);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}

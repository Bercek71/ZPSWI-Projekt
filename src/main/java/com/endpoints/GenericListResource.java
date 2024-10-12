package com.endpoints;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.Hibernate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

//Trošku hacky, ale prozatím bych takto nechal.
@Path("/{entity}")
public class GenericListResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response GenericGet(@PathParam("entity") String entity, @QueryParam("expand") String expand) {
        try {
            String formattedEntity = capitalizeFirstLetter(entity);
            Class<?> entityClass = Class.forName("com.persistence." + formattedEntity);
            Method listAllMethod = entityClass.getMethod("listAll");
            List<?> entities = (List<?>) listAllMethod.invoke(null);

            if (expand != null) {
                String[] expandList = expand.split(",");
                for (Object entityInstance : entities) {
                    for (String relation : expandList) {
                        initializeRelation(entityInstance, relation.trim());
                    }
                }
            }

            return Response.ok(entities).build();
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        } catch (InvocationTargetException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getTargetException().getMessage()).build();
        } catch (NoSuchMethodException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Method not found").build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Method not accessible").build();
        }
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private void initializeRelation(Object entityInstance, String relation) {
        try {
            String methodName = "get" + relation;
            Collection<Method> methods = Arrays.asList(entityInstance.getClass().getMethods());
            Method getRelationMethod = methods.stream().filter(method -> method.getName().equalsIgnoreCase(methodName)).findFirst().orElse(null);
            assert getRelationMethod != null;
            Object relationObject = getRelationMethod.invoke(entityInstance);

            if (relationObject instanceof List<?> relations) {
                for (Object relationItem : relations) {
                    if (relationItem != null && !Hibernate.isInitialized(relationItem)) {
                        Hibernate.initialize(relationItem);
                    }
                }
            } else {
                if (relationObject != null && !Hibernate.isInitialized(relationObject)) {
                    Hibernate.initialize(relationObject);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException  | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
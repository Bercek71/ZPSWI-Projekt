package com.resources;

import com.persistence.AppUser;
import com.persistence.Hotel;
import io.quarkus.security.Authenticated;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("users")
public class UserResource implements Resource<AppUser> {

    @GET
    @Path("{id}/hotels")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response getOwnedHotels(@PathParam("id") Long ownerId){
        List<Hotel> hotels = Hotel.findAllOwnedHotels(ownerId);
        if(hotels.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"No hotel was found.\"}")
                    .build();
        }
        return Response.ok(hotels).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<AppUser> users = AppUser.listAll();
        if (users.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"No user was found.\"}")
                    .build();
        }
        return Response.ok(users)
                .build();
    }

    @Override
    public Response find(Long filter) {
        AppUser user = AppUser.findById(filter);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"User not found.\"}")
                    .build();
        }
        return Response.ok(user)
                .build();
    }

    @Transactional
    @Override
    public Response create(AppUser user) {
        try {
            user.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

    @Transactional
    @Override
    public Response update(Long id, AppUser user) {
        AppUser updateUser = AppUser.findById(id);

        if (updateUser == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"User not found.\"}")
                    .build();
        }

        try {
            updateUser.firstName = user.firstName;
            updateUser.lastName = user.lastName;
            updateUser.email = user.email;
            updateUser.password = user.password;
            updateUser.role = user.role;

            updateUser.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.ok(updateUser)
                .build();
    }

    @Transactional
    @Override
    public Response delete(Long id) {
        //Need to check for constraints
        AppUser user = AppUser.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"User not found.\"}")
                    .build();
        }
        try {
            user.delete();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(user)
                .build();
    }
}

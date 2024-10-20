package com.endpoints;

import com.persistence.AppUser;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("users")
public class UserResource implements Resource<AppUser> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<AppUser> users = AppUser.listAll();
        if(users.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(users).build();
    }

    @Override
    public Response find(Long filter) {
        AppUser user = AppUser.findById(filter);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @Transactional
    @Override
    public Response create(AppUser user) {
        try{
            user.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @Override
    public Response update(Long id, AppUser user) {
        AppUser updateUser = AppUser.findById(id);

        if(updateUser == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Hotel not found.").build();
        }

        try{
            updateUser.firstName = user.firstName;
            updateUser.lastName = user.lastName;
            updateUser.email = user.email;
            updateUser.password = user.password;
            updateUser.role = user.role;

            updateUser.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.ok(updateUser).build();
    }


    @Transactional
    @Override
    public Response delete(Long id) {
        //Need to check for constraints
        AppUser user = AppUser.findById(id);
        if(user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found.").build();
        }
        try{
            user.delete();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.OK).entity("Entity " + user + "deleted.").build();
    }
}

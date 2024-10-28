package com.authorization;

import com.persistence.AppUser;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
public class Authorization {

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        AppUser user = AppUser.find("email", email).firstResult();

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Wrong email").build();
        }
        if (!user.password.equals(password)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Wrong password").build();
        }
        return Response.ok().entity("Authorized").build();
    }

    @POST
    @Path("register")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(AppUser user) {
        try {
            if(user == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Wrong body").build();
            }
            user.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.CREATED).entity("Successfully created new user").build();
    }
}

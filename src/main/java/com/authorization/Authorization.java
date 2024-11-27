package com.authorization;

import com.persistence.AppUser;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.mindrot.jbcrypt.BCrypt;
import io.smallrye.jwt.build.Jwt;

import java.time.Instant;

@Path("")
public class Authorization {

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AppUser user) {

        AppUser existingUser = AppUser.find("email", user.email).firstResult();

        if (existingUser == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"msg\": \"Wrong email\"}")
                    .build();
        }

        if (!BCrypt.checkpw(user.password, existingUser.password)){
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"msg\": \"Wrong password\"}")
                    .build();
        }

        //Momentálně Secret key na pevno, při real-life využití, je třeba toto mít schované v nějakém vaultu, popřípadě na serveru, aby to nešlo na git.
        String token = Jwt.subject(existingUser.email)
                .groups(existingUser.role.toString())
                .issuedAt(Instant.now().getEpochSecond())
                .expiresAt(System.currentTimeMillis() / 1000 + 3600) //1 hodina MAGIC NUMBERS
                .sign();

        return Response.ok()
                .entity("{\"token\": \"" + token + "\"}")
                .build();
    }

    @POST
    @Path("register")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(AppUser user) {
        try {
            if(user == null || user.firstName == null || user.lastName == null || user.email == null || user.password == null || user.role == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"msg\": \"Wrong body.\"}")
                        .build();
            }
            user.password = BCrypt.hashpw(user.password, BCrypt.gensalt());
            user.persist();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

    @GET
    @Path("authorized")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response getUserFromToken() {
        AppUser user;
        try {
            user = AppUser.find("email", jwt.getSubject()).firstResult();
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"msg\": \"User not found.\"}")
                        .build();
            }
            user.password = null;

        } catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e)
                    .build();
        }

        return Response.status(Response.Status.OK)
                .entity(user)
                .build();
    }
}

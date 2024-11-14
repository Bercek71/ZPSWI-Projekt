package com.authorization;

import com.persistence.AppUser;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;
import io.smallrye.jwt.build.Jwt;

import java.time.Instant;

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
        if (!BCrypt.checkpw(password, user.password)){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Wrong password").build();
        }

        //Momentálně Secret key na pevno, při real-life využití, je třeba toto mít schované v nějakém vaultu, popřípadě na serveru, aby to nešlo na git.
        String token = Jwt.subject(user.email)
                .claim("role", user.role)
                .issuedAt(Instant.now().getEpochSecond())
                .expiresAt(System.currentTimeMillis() / 1000 + 3600)
                .signWithSecret("T9BAmve6Z3SynHgspogUuEcPTo1LZrQRZorlPnpw1Tk=");

        return Response.ok().entity("Authorized... {" + token + "}").build();
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
            user.password = BCrypt.hashpw(user.password, BCrypt.gensalt());
            user.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.CREATED).entity("Successfully created new user").build();
    }
}

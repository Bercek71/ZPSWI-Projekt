package com.authorization;

import com.persistence.AppUser;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
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
            return Response.status(Response.Status.NOT_FOUND).entity("Wrong email " + user.email).build();
        }

        if (!BCrypt.checkpw(user.password, existingUser.password)){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Wrong password " + user.email).build();
        }

        HashSet hs = new HashSet<>();
        hs.add(existingUser.role.toString());
        //Momentálně Secret key na pevno, při real-life využití, je třeba toto mít schované v nějakém vaultu, popřípadě na serveru, aby to nešlo na git.
        String token = Jwt.subject(existingUser.email)
                .claim("roles",existingUser.role.toString())
                //.issuedAt(Instant.now().getEpochSecond())
                //.expiresAt(System.currentTimeMillis() / 1000 + 3600)
                .signWithSecret("T9BAmve6Z3SynHgspogUuEcPTo1LZrQRZorlPnpw1Tk=");

        System.out.println(existingUser.role.toString());

        return Response.ok().entity(token).build();
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

    @GET
    @Path("authorized")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("abc")
    public Response getUserFromToken() {
        //RETURN USER WITHOUT PASSWORD
        AppUser user = null;
        return Response.status(Response.Status.OK).build();
        //return null;
    }
}

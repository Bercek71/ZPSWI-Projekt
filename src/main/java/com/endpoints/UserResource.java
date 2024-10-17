package com.endpoints;

import com.persistence.AppUser;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("users")
public class UserResource extends PanacheEntity implements Resource<AppUser> {

    @Override
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

    @Override
    public Response create(PanacheEntity entity) {
        try{
            entity.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Override
    public Response update(PanacheEntity entity) {
        return null;
    }

    @Override
    public Response delete(Long id) {
        return null;
    }
}

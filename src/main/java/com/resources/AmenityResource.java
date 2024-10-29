package com.resources;

import com.persistence.Amenity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("amenities")
public class AmenityResource implements Resource<Amenity>{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<Amenity> amenities = Amenity.listAll();
        if (amenities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(amenities).build();
    }

    @Override
    public Response find(Long filter) {
        Amenity amenity = Amenity.findById(filter);
        if (amenity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(amenity).build();
    }

    @Transactional
    @Override
    public Response create(Amenity entity) {
        try{
            entity.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Amenity entity) {
        {
            Amenity updateAmenity = Amenity.findById(id);

            if (updateAmenity == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Amenity not found.").build();
            }

            try {
                updateAmenity.name = entity.name;

                updateAmenity.persist();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
            }
            return Response.ok(updateAmenity).build();
        }
    }

    @Override
    public Response delete(Long id) {
        return null;
    }
}
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
            return Response.status(Response.Status.NOT_FOUND).entity("{msg: 'No amenity was found.'}").build();
        }
        return Response.ok(amenities).build();
    }

    @Override
    public Response find(Long filter) {
        Amenity amenity = Amenity.findById(filter);
        if (amenity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{msg: 'Amenity not found.'}").build();
        }
        return Response.ok(amenity).build();
    }

    @Transactional
    @Override
    public Response create(Amenity amenity) {
        try{
            amenity.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{msg: '" + e.getMessage() + "'}").build();
        }
        return Response.status(Response.Status.CREATED).entity(amenity).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Amenity entity) {
        {
            Amenity updateAmenity = Amenity.findById(id);

            if (updateAmenity == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{msg: 'Amenity not found.'}").build();
            }

            try {
                updateAmenity.name = entity.name;

                updateAmenity.persist();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{msg: '" + e.getMessage() + "'}").build();
            }
            return Response.ok(updateAmenity).build();
        }
    }

    @Override
    public Response delete(Long id) {
        return null;
    }
}

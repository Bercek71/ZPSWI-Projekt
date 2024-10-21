package com.endpoints;

import com.persistence.Amenity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("amenities")
public class AmenityResource implements Resource<Amenity>{

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
                updateAmenity.wifi = entity.wifi;
                updateAmenity.singleBed = entity.singleBed;
                updateAmenity.doubleBed = entity.doubleBed;
                updateAmenity.bunkBed = entity.bunkBed;
                updateAmenity.kingBed = entity.kingBed;
                updateAmenity.minibar = entity.minibar;
                updateAmenity.tv = entity.tv;
                updateAmenity.roomService = entity.roomService;
                updateAmenity.balcony = entity.balcony;
                updateAmenity.bathroom = entity.bathroom;
                updateAmenity.board = entity.board;

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

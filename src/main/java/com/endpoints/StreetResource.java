package com.endpoints;

import com.persistence.City;
import com.persistence.Street;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("streets")
public class StreetResource extends PanacheEntity implements Resource<Street> {

    @Path("streets")
    public Response findAllEntities() {
        List<Street> streets = Street.listAll();
        if(streets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(streets).build();
    }

    @Override
    public Response find(Long filter) {
        Street street = Street.findById(filter);
        if (street == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(street).build();
    }

    @Transactional
    @Override
    public Response create(Street street) {
        try{
            street.city = CityResource.findById(street.cityId);
            street.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Street street) {
        Street updateStreet = Street.findById(id);

        if(updateStreet == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Street not found.").build();
        }

        try{
            updateStreet.name = street.name;
            updateStreet.landRegistryNumber = street.landRegistryNumber;
            updateStreet.houseNumber = street.houseNumber;
            updateStreet.city = City.findById(street.cityId);

            updateStreet.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.ok(updateStreet).build();
    }


    @Transactional
    @Override
    public Response delete(Long id) {
        return null;
    }
}

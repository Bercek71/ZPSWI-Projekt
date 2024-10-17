package com.endpoints;

import com.persistence.AppUser;
import com.persistence.Booking;
import com.persistence.Country;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class CountryResource extends PanacheEntity implements Resource<Country> {

    @Override
    public Response findAllEntities() {
        List<Country> countries = Country.listAll();
        if(countries.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(countries).build();
    }

    @Override
    public Response find(Long filter) {
        Country country = Country.findById(filter);
        if (country == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(country).build();
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

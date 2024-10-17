package com.endpoints;

import com.persistence.AppUser;
import com.persistence.Booking;
import com.persistence.City;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class CityResource  extends PanacheEntity implements Resource<City> {

    @Override
    public Response findAllEntities() {
        List<City> cities = City.listAll();
        if(cities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(cities).build();
    }

    @Override
    public Response find(Long filter) {
        City city = City.findById(filter);
        if (city == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(city).build();
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

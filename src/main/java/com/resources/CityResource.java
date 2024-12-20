package com.resources;

import com.persistence.City;
import com.persistence.Country;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("cities")
public class CityResource implements Resource<City> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<City> cities = City.listAll();
        if (cities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"No city was found.\"}")
                    .build();
        }
        return Response.ok(cities).build();
    }

    @Override
    public Response find(Long filter) {
        City city = City.findById(filter);
        if (city == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"City not found.\"}")
                    .build();
        }
        return Response.ok(city).build();
    }

    @Override
    public Response create(City city) {
        try {
            city.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(city)
                .build();
    }

    @Transactional
    @Override
    public Response update(Long id, City city) {
        City updateCity = City.findById(id);

        if (updateCity == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"City not found.\"}")
                    .build();
        }

        try {
            updateCity.country = city.country;
            updateCity.name = city.name;
            updateCity.zipCode = city.zipCode;

            updateCity.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.ok(updateCity).build();
    }

    @Transactional
    @Override
    public Response delete(Long id) {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .entity("{\"msg\": \"Method not implemented.\"}")
                .build();
    }
}

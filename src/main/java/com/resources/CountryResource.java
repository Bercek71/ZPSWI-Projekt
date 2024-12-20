package com.resources;

import com.persistence.Country;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("countries")
public class CountryResource implements Resource<Country> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<Country> countries = Country.listAll();
        if (countries.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"No country was found.\"}")
                    .build();
        }
        return Response.ok(countries)
                .build();
    }

    @Override
    public Response find(Long filter) {
        Country country = Country.findById(filter);
        if (country == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"Country not found.\"}")
                    .build();
        }
        return Response.ok(country)
                .build();
    }

    @Transactional
    @Override
    public Response create(Country country) {
        try {
            country.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(country)
                .build();
    }

    @Transactional
    @Override
    public Response update(Long id, Country country) {
        Country updateCountry = Country.findById(id);

        if (updateCountry == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"Country not found.\"}")
                    .build();
        }

        try {
            updateCountry.name = country.name;
            updateCountry.isoCode = country.isoCode;

            updateCountry.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.ok(updateCountry).build();
    }

    @Transactional
    @Override
    public Response delete(Long id) {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .entity("{\"msg\": \"Method not implemented.\"}")
                .build();
    }
}

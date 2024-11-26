package com.resources;

import com.persistence.Address;
import com.persistence.City;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("addresses")
public class AddressResource implements Resource<Address> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEntities() {
        List<Address> addresses = Address.listAll();
        if (addresses.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"No address was found.\"}")
                    .build();
        }
        return Response.ok(addresses)
                .build();
    }

    @Override
    public Response find(Long filter) {
        Address address = Address.findById(filter);
        if (address == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"Address not found.\"}")
                    .build();
        }
        return Response.ok(address)
                .build();
    }

    @Transactional
    @Override
    public Response create(Address address) {
        try {
            address.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(address)
                .build();
    }

    @Transactional
    @Override
    public Response update(Long id, Address address) {
        Address updateAddress = Address.findById(id);

        if(address.city == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"Wrong body\"}")
                    .build();
        }

        if (updateAddress == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"msg\": \"Street not found.\"}")
                    .build();
        }

        try {
            updateAddress.name = address.name;
            updateAddress.landRegistryNumber = address.landRegistryNumber;
            updateAddress.houseNumber = address.houseNumber;
            updateAddress.city = address.city;

            updateAddress.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"msg\": \"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.ok(updateAddress).build();
    }


    @Transactional
    @Override
    public Response delete(Long id) {
        return null;
    }
}

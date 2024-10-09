package com.example;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


//Ukázka použití JPA entity a transakce v REST endpointu
@Path("/hello")
public class ExampleResource {
    private int counter = 0;
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public MyEntity hello() {
        MyEntity entity1 = new MyEntity();
        entity1.field = "field-" + counter++;
        entity1.persist();
        return entity1;
    }
}

package com.example;

import com.persistence.Room;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ExampleResourceTest {
    @Test
    void testValidRequest() {
        given()
          .when().get("/rooms")
          .then()
             .statusCode(200);
    }

    @Test
    void testInvalidRequest() {
        given()
          .when().get("/rooms/afjsgkj")
          .then()
             .statusCode(404);
    }

}
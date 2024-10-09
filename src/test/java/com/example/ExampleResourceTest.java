package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

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
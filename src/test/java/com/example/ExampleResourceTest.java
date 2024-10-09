package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ExampleResourceTest {
    @Test
    void testRoomEndPoint() {
        given()
          .when().get("/rooms")
          .then()
             .statusCode(200);
    }

}
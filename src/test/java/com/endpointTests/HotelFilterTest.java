package com.endpointTests;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class HotelFilterTest {

    @Test
    void testValidRequest() {
        given()
            .when().get("/hotels?checkIn=2021-12-01&checkOut=2021-12-10&guests=2&cityId=1")
            .then()
                 .statusCode(200);
    }

    @Test
    void testMissingParameterTest() {
        given()
            .when().get("/hotels?checkIn=2021-12-01&checkOut=2021-12-10&guests=2")
            .then()
                 .statusCode(400);
    }

    @Test
    void invalidDateFormatTest(){
        given()
            .when().get("/hotels?checkIn=12-01-2021&checkOut=2021-12-10&guests=2&cityId=1")
            .then()
                 .statusCode(500)
                 .extract().response().asString().contains("Bad Request");
    }

    @Test
    void testValidRequestWithNoParameters() {
        given()
            .when().get("/hotels")
            .then()
                 .statusCode(200);
    }

    @Test
    void testValidRequestWithInvalidCity() {
        given()
            .when().get("/hotels?checkIn=2021-12-01&checkOut=2021-12-10&guests=2&cityId=100")
            .then()
                 .statusCode(404);
    }

    @Test
    void testInvalidRequest() {
        given()
            .when().get("/hotels/afjsgkj")
            .then()
                 .statusCode(404);
    }
}

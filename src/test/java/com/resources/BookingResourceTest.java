package com.resources;

import com.persistence.Booking;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestHTTPEndpoint(BookingResource.class)
public class BookingResourceTest {

    @Test
    void findAllShouldAlwaysReturnListAndOK() {
        when()
            .get()
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body(matchesPattern("^\\s*\\[.*\\]\\s*$"));
    }

    @Test
    void findWithStringParameterShouldReturnBadRequest() {
        given()
            .pathParam("id", "aaaa")
        .when()
            .get("{id}")
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void findWithFloatParameterShouldReturnBadRequest() {
        given()
            .pathParam("id", 1.23)
        .when()
            .get("{id}")
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void findWithValidParameterShouldReturnOKOrNotFound() {
        given()
            .pathParam("id", new Random().nextLong())
        .when()
            .get("{id}")
        .then()
            .statusCode(is(in(Arrays.asList(
                    Response.Status.OK.getStatusCode(),
                    Response.Status.NOT_FOUND.getStatusCode())
            )));
    }

    @Test
    void createWithValidBodyShouldReturnCreated() {
        JsonObjectBuilder reservation = Json.createObjectBuilder()
            .add("startDate", LocalDate.now().toString())
            .add("endDate", LocalDate.now().plusDays(1).toString())
            .add("roomId", 1);
        JsonArrayBuilder reservations = Json.createArrayBuilder()
            .add(reservation);
        JsonObjectBuilder booking = Json.createObjectBuilder()
            .add("userId", 1)
            .add("reservations", reservations);

        given()
            .header("Content-Type", "application/json")
            .body(booking.build().toString())
        .when()
            .post()
        .then()
            .statusCode(is(Response.Status.CREATED.getStatusCode()));
    }

    @Test
    void createWithValidBodyAndNonExistingUserIdShouldReturnInternalServerError() {
        LocalDate now = LocalDate.now();
        JsonObjectBuilder reservation = Json.createObjectBuilder()
            .add("startDate", now.toString())
            .add("endDate", now.plusDays(1).toString())
            .add("roomId", 1);
        JsonArrayBuilder reservations = Json.createArrayBuilder()
            .add(reservation);
        JsonObjectBuilder booking = Json.createObjectBuilder()
            .add("userId", Long.MAX_VALUE)
            .add("reservations", reservations);

        given()
            .header("Content-Type", "application/json")
            .body(booking.build().toString())
        .when()
            .post()
        .then()
            .statusCode(is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
    }

    @Test
    void createWithEmptyBodyShouldReturnBadRequest() {
        given()
            .header("Content-Type", "application/json")
            .body("")
        .when()
            .post()
        .then()
            .statusCode(is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    void createWithStartDateLaterThanEndDateShouldReturnBadRequest() {
        LocalDate now = LocalDate.now();
        JsonObjectBuilder reservation = Json.createObjectBuilder()
            .add("startDate", now.plusDays(1).toString())
            .add("endDate", now.toString());
        JsonArrayBuilder reservations = Json.createArrayBuilder()
            .add(reservation);
        JsonObjectBuilder booking = Json.createObjectBuilder()
            .add("userId", 1)
            .add("reservations", reservations);

        given()
            .header("Content-Type", "application/json")
            .body(booking.build().toString())
        .when()
            .post()
        .then()
            .statusCode(is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    void createWithEmptyReservationsShouldReturnBadRequest() {
        JsonObjectBuilder booking = Json.createObjectBuilder()
            .add("userId", 1)
            .add("reservations", Json.createArrayBuilder());

        given()
            .header("Content-Type", "application/json")
            .body(booking.build().toString())
        .when()
            .post()
        .then()
            .statusCode(is(Response.Status.BAD_REQUEST.getStatusCode()));
    }
    @Test
    void updateShouldReturnNotImplemented() {
        Booking request = new Booking();
        request.priceTotal = 100;
        request.userId = 1L;

        given()
            .header("Content-Type", "application/json")
            .body(JsonbBuilder.create().toJson(request))
            .pathParam("id", 1)
        .when()
            .put("{id}")
        .then()
            .statusCode(is(Response.Status.NOT_IMPLEMENTED.getStatusCode()));
    }
}

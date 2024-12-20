package com.resources;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(ReservationResource.class)
public class ReservationResourceTest {
	@Test
	void findAllShouldAlwaysReturnListAndOK() {
		when()
			.get()
		.then()
			.statusCode(Response.Status.OK.getStatusCode())
			.body(matchesPattern("^\\s*\\[.*\\]\\s*$"));
	}

	@Test
	void findWithStringParameterShouldReturnNotFound() {
		given()
			.pathParam("id", "aaaa")
		.when()
			.get("{id}")
		.then()
			.statusCode(Response.Status.NOT_FOUND.getStatusCode());
	}

	@Test
	void findWithFloatParameterShouldReturnNotFound() {
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
	void createShouldReturnNotImplemented() {
		given()
			.header("Content-Type", "application/json")
			.body("")
		.when()
			.post()
		.then()
			.statusCode(is(Response.Status.NOT_IMPLEMENTED.getStatusCode()));
	}

	@Test
	void updateWithEmptyBodyShouldReturnBadRequest() {
		given()
			.header("Content-Type", "application/json")
			.pathParam("id", 1)
			.body("")
		.when()
			.put("{id}")
		.then()
			.statusCode(is(Response.Status.BAD_REQUEST.getStatusCode()));
	}
}

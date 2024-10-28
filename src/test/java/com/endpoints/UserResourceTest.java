package com.endpoints;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestHTTPEndpoint(UserResource.class)
public class UserResourceTest {

	@Test
	void findAllShouldAlwaysReturnListAndOK() {
		when()
			.get()
		.then()
			.statusCode(Response.Status.OK.getStatusCode())
			.body(matchesPattern("^\\s*\\[.*\\]\\s*$"));
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
}

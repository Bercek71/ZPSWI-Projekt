package com.endpoints;

import com.persistence.*;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestHTTPEndpoint(HotelResource.class)
public class HotelResourceTest {

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
	void createWithValidBodyShouldReturnCreated() {
		JsonObjectBuilder country = Json.createObjectBuilder()
            .add("isoCode", "USA")
			.add("name", "United States");
		JsonObjectBuilder city = Json.createObjectBuilder()
			.add("country", country)
			.add("name", "San Francisco")
			.add("zipCode", 12345);
		JsonObjectBuilder address = Json.createObjectBuilder()
			.add("city", city)
			.add("name", "Golden Eye's")
			.add("houseNumber", 12345)
			.add("landRegistryNumber", 12345);
		JsonObjectBuilder hotel = Json.createObjectBuilder()
			.add("address", address)
			.add("name", "TestHotel");

		given()
			.header("Content-Type", "application/json")
			.body(hotel.build().toString())
		.when()
			.post()
		.then()
			.statusCode(is(Response.Status.CREATED.getStatusCode()));
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
	void updateWithValidBodyShouldReturnOK() {
		JsonObjectBuilder hotel = Json.createObjectBuilder()
            .add("name", "TestHotelRename");

		given()
			.header("Content-Type", "application/json")
			.body(hotel.build().toString())
			.pathParam("id", 1)
		.when()
			.put("{id}")
		.then()
			.statusCode(is(Response.Status.OK.getStatusCode()));
	}

	@Test
	void updateWithNonExistingIdAndValidBodyShouldReturnNotFound() {
		JsonObjectBuilder hotel = Json.createObjectBuilder()
			.add("name", "TestHotelRename");

		given()
			.header("Content-Type", "application/json")
			.body(hotel.build().toString())
			.pathParam("id", Long.MAX_VALUE)
		.when()
			.put("{id}")
		.then()
			.statusCode(is(Response.Status.NOT_FOUND.getStatusCode()));
	}
}

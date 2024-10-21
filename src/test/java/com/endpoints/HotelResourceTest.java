package com.endpoints;

import com.persistence.Booking;
import com.persistence.Hotel;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
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
	void createWithValidBodyAndValidCountryIdShouldReturnCreated() {
		Hotel request = new Hotel();
		request.addressId = 1L;
		request.name = "TestHotel";

		given()
			.header("Content-Type", "application/json")
			.body(JsonbBuilder.create()
			                  .toJson(request))
		.when()
			.post()
		.then()
			.statusCode(is(Response.Status.CREATED.getStatusCode()));
	}

	@Test
	void createWithValidBodyAndNonExistingCountryIdShouldReturnInternalServerError() {
		Hotel request = new Hotel();
		request.addressId = Long.MAX_VALUE;
		request.name = "TestHotel";

		given()
			.header("Content-Type", "application/json")
			.body(JsonbBuilder.create()
			                  .toJson(request))
		.when()
			.post()
		.then()
			.statusCode(is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
	}

	@Test
	void createWithInvalidBodyShouldReturnBadRequest() {
		given()
			.header("Content-Type", "application/json")
			.body("")
		.when()
			.post()
		.then()
			.statusCode(is(Response.Status.BAD_REQUEST.getStatusCode()));
	}

	@Test
	void updateWithExistingIdValidBodyAndValidCountryIdShouldReturnOK() {
		Hotel request = new Hotel();
		request.addressId = 1L;
		request.name = "TestHotelRename";

		given()
			.header("Content-Type", "application/json")
			.body(JsonbBuilder.create()
			                  .toJson(request))
			.pathParam("id", 1)
		.when()
			.put("{id}")
		.then()
			.statusCode(is(Response.Status.OK.getStatusCode()));
	}

	@Test
	void updateWithExistingIdAndValidBodyAndNonExistingCountryIdShouldReturnInternalServerError() {
		Hotel request = new Hotel();
		request.addressId = Long.MAX_VALUE;
		request.name = "TestHotelRename";

		given()
			.header("Content-Type", "application/json")
			.body(JsonbBuilder.create()
			                  .toJson(request))
			.pathParam("id", 1)
		.when()
			.put("{id}")
		.then()
			.statusCode(is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
	}

	@Test
	void updateWithNonExistingIdAndValidBodyShouldReturnNotFound() {
		Hotel request = new Hotel();
		request.addressId = 1L;
		request.name = "TestHotelRename";

		given()
			.header("Content-Type", "application/json")
			.body(JsonbBuilder.create()
			                  .toJson(request))
			.pathParam("id", Long.MAX_VALUE)
		.when()
			.put("{id}")
		.then()
			.statusCode(is(Response.Status.NOT_FOUND.getStatusCode()));
	}
}

package com.resources;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.matchesPattern;

@QuarkusTest
@TestHTTPEndpoint(RoomResource.class)
public class RoomResourceTest {

	@Test
	void findAllShouldAlwaysReturnListAndOK() {
		when()
			.get()
		.then()
			.statusCode(Response.Status.OK.getStatusCode())
			.body(matchesPattern("^\\s*\\[.*\\]\\s*$"));
	}
}

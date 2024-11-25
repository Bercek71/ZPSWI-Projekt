package com.authorization;

import com.resources.AddressResource;
import io.quarkus.logging.Log;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestHTTPEndpoint(Authorization.class)
public class AuthorizationTest {

	private boolean isValidJson(String json)
	{
		try {
			Json.createReader(new StringReader(json)).readObject();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Test
	void registerWithValidBodyShouldReturnCreated() {
		JsonObjectBuilder user = Json.createObjectBuilder()
            .add("firstName", "Pepik")
            .add("lastName", "Brown")
            .add("email", "pepik.brown@gmail.com")
            .add("password", "pepik")
			.add("role", "MANAGER");
		var response = given()
			.header("Content-Type", "application/json")
			.body(user.build().toString())
		.when()
			.post("register")
		.then()
			.statusCode(Response.Status.CREATED.getStatusCode());
//		String json = response.extract().body().asString();
//		assertTrue(isValidJson(json));
	}

	@Test
	void registerWithInvalidBodyShouldReturnBadRequest() {
		JsonObjectBuilder user = Json.createObjectBuilder()
	        .add("lastName", "Brown")
	        .add("password", "pepik")
	        .add("role", "MANAGER");
		given()
			.header("Content-Type", "application/json")
			.body(user.build().toString())
		.when()
			.post("register")
		.then()
			.statusCode(Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Test
	void loginWithValidBodyShouldReturnOK() {
		JsonObjectBuilder user = Json.createObjectBuilder()
            .add("email", "pepik.brown@gmail.com")
            .add("password", "pepik");
		given()
			.header("Content-Type", "application/json")
			.body(user.build().toString())
		.when()
			.post("login")
		.then()
			.statusCode(Response.Status.OK.getStatusCode());
	}

	@Test
	void loginWithInvalidBodyShouldReturnBadRequest() {
		JsonObjectBuilder user = Json.createObjectBuilder()
            .add("password", "pepik");
		given()
			.header("Content-Type", "application/json")
			.body(user.build().toString())
		.when()
			.post("login")
		.then()
			.statusCode(Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Test
	void loginWithInvalidPasswordShouldReturnUnauthorized() {
		JsonObjectBuilder user = Json.createObjectBuilder()
            .add("email", "pepik.brown@gmail.com")
            .add("password", "aaa");
		given()
			.header("Content-Type", "application/json")
			.body(user.build().toString())
		.when()
			.post("login")
		.then()
			.statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
	}

	@Test
	void authorizedWithValidTokenShouldReturnOK() {
		JsonObjectBuilder user = Json.createObjectBuilder()
		                             .add("firstName", "Pepik")
		                             .add("lastName", "Brown")
		                             .add("email", "pepik1.brown@gmail.com")
		                             .add("password", "pepik")
		                             .add("role", "MANAGER");
		var response = given()
			.header("Content-Type", "application/json")
			.body(user.build().toString())
			.when()
			.post("register")
			.then()
			.statusCode(Response.Status.CREATED.getStatusCode());

		user = Json.createObjectBuilder()
		                             .add("email", "pepik1.brown@gmail.com")
		                             .add("password", "pepik");
		var loginResponse = given()
			.header("Content-Type", "application/json")
			.body(user.build().toString())
		.when()
			.post("login");
		String body = loginResponse.body().asString();
		Log.info(loginResponse.body().asString());
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + body)
		.when()
			.get("authorized")
		.then()
			.statusCode(Response.Status.OK.getStatusCode());
	}

	@Test
	void authorizedWithInvalidTokenShouldReturnUnauthorized() {
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer a")
		.when()
			.get("authorized")
		.then()
			.statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
	}




}

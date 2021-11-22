package com.nia.assessment.filter;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class LoginTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @DisplayName("When calling '/api/login' endpoint with correct credentials should receive back access token and refresh token")
    @Test
    void test_login_with_correct_credentials_should_return_tokens() {

        given()
            .log().all()
            .contentType("application/x-www-form-urlencoded; charset=utf-8")
            .formParam("username", "orlando")
            .formParam("password", "1234")
            .when().post("/api/login")
        .then()
            .log().all()
            .statusCode(200)
            .body("status", is(200))
            .body("message", is("SUCCESS"))
            .body("payload.access_token", notNullValue())
            .body("payload.refresh_token", notNullValue());
    }
}

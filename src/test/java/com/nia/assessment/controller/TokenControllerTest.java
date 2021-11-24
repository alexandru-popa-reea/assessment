package com.nia.assessment.controller;

import com.nia.assessment.utils.ObjectUtils;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class TokenControllerTest {

    @Autowired
    TokenController tokenController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    private static String refreshToken;
    private static String accessToken;

    @DisplayName("When calling generate token endpoint with correct user and password it should respond with 200 and 2 tokens")
    @Test
    @Order(1)
    void when_calling_generate_token_endpoint_with_good_credentials_it_should_respond_with_2_tokens() {

        UserDto userDto = new UserDto("orlando", "1234");

        refreshToken = given()
                .log().all()
                .contentType(APPLICATION_JSON_VALUE)
                .body(ObjectUtils.getSerializedObjectOrNull(userDto))
                .when()
                .post("/api/token/generate")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is(200))
                .body("message", is("SUCCESS"))
                .body("payload.access_token", notNullValue())
                .body("payload.refresh_token", notNullValue())
                .extract().path("payload.refresh_token");
    }

    @DisplayName("When calling generate token endpoint with wrong user and password it should respond with 403")
    @Test
    @Order(2)
    void when_calling_generate_token_endpoint_with_bad_credentials_it_should_respond_with_403() {

        UserDto userDto = new UserDto("orlando", "12345");

        Map<String, String> payload = given()
                .log().all()
                .contentType(APPLICATION_JSON_VALUE)
                .body(ObjectUtils.getSerializedObjectOrNull(userDto))
                .when()
                .post("/api/token/generate")
                .then()
                .log().all()
                .statusCode(403)
                .body("status", is(403))
                .body("message", is("Bad credentials"))
                .body("payload", notNullValue())
                .extract().path("payload");
        then(payload.size()).isEqualTo(0);
    }

    @DisplayName("When calling refresh token with correct token should respond with 200")
    @Test
    @Order(3)
    void when_calling_generate_refresh_token_with_correct_token_should_respond_with_200() {

        accessToken = given()
                .log().all()
                .header("Authorization", "Bearer " + refreshToken)
                .header("Accept", APPLICATION_JSON_VALUE)
                .when()
                .get("/api/token/refresh")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is(200))
                .body("message", is("SUCCESS"))
                .body("payload.access_token", notNullValue())
                .body("payload.refresh_token", notNullValue())
                .extract().path("payload.access_token");
    }

    @DisplayName("When calling refresh token with incorrect constructed token should respond with 500")
    @Test
    @Order(4)
    void when_calling_generate_refresh_token_with_incorrect_constructed_token_should_respond_with_500() {

        Map<String, String> payload = given()
                .log().all()
                .header("Authorization", "Bearer " + refreshToken + "x")
                .header("Accept", APPLICATION_JSON_VALUE)
                .when()
                .get("/api/token/refresh")
                .then()
                .log().all()
                .statusCode(500)
                .body("status", is(500))
                .body("message", is("The Token's Signature resulted invalid when verified using the Algorithm: HmacSHA256"))
                .extract().path("payload");
        then(payload.size()).isEqualTo(0);
    }

    @DisplayName("When calling refresh token with wrong token should respond with 403")
    @Test
    @Order(5)
    void when_calling_generate_refresh_token_with_wrong_token_should_respond_with_403() {

        Map<String, String> payload = given()
                .log().all()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", APPLICATION_JSON_VALUE)
                .when()
                .get("/api/token/refresh")
                .then()
                .log().all()
                .statusCode(403)
                .body("status", is(403))
                .body("message", is("The call was not made with a valid refresh token"))
                .extract().path("payload");
        then(payload.size()).isEqualTo(0);
    }

    @DisplayName("Testing UserDto (username and password)")
    @Test
    @Order(6)
    void test_UserDto() {

        //given
        UserDto userDto = new UserDto();

        //when // then
        then(userDto.getUsername()).isNull();
        then(userDto.getPassword()).isNull();


        //given
        userDto = new UserDto("aaa", "bbb");

        //when // then
        then(userDto.getUsername()).isEqualTo("aaa");
        then(userDto.getPassword()).isEqualTo("bbb");

        //given
        userDto = new UserDto();
        userDto.setUsername("aaa");
        userDto.setPassword("bbb");

        //when // then
        then(userDto.getUsername()).isEqualTo("aaa");
        then(userDto.getPassword()).isEqualTo("bbb");


        //given
        userDto = UserDto.builder().username("aaa").password("bbb").build();

        //when // then
        then(userDto.getUsername()).isEqualTo("aaa");
        then(userDto.getPassword()).isEqualTo("bbb");
    }
}
package com.nia.assessment.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.config.MockMvcConfig.mockMvcConfig;
import static io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig.config;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class PingControllerTest {

    @InjectMocks
    private PingController pingController;

    @BeforeEach
    void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(pingController);
    }

    RestAssuredMockMvcConfig noSecurity() {
        return config().mockMvcConfig(mockMvcConfig()
                .dontAutomaticallyApplySpringSecurityMockMvcConfigurer());
    }

    @DisplayName("When pinging the application should respond that 'is running")
    @Test
    void when_calling_ping_endpoint_should_respond_is_running() {

        given()
            .config(noSecurity())
            .log().all()
            .when()
            .get("/api/ping")
            .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("message", is("Application is running"));
    }
}
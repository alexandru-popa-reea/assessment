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
import static org.assertj.core.api.BDDAssertions.then;
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

    @DisplayName("Test internal Dto -> PingDto -> builder")
    @Test
    void test_internal_PingDto_builder() {

        //given
        PingDto pingDto = PingDto.builder().code(200).message("SUCCESS").build();

        //when //then
        then(pingDto.getCode()).isEqualTo(200);
        then(pingDto.getMessage()).isEqualTo("SUCCESS");
    }

    @DisplayName("Test internal Dto -> PingDto --> getters and setters")
    @Test
    void test_internal_PingDto_getters_and_Setters() {

        //given
        PingDto pingDto = new PingDto();

        //when
        pingDto.setCode(200);
        pingDto.setMessage("SUCCESS");

        //then
        then(pingDto.getCode()).isEqualTo(200);
        then(pingDto.getMessage()).isEqualTo("SUCCESS");
    }

    @DisplayName("Test internal Dto -> PingDto --> equals and hashcode")
    @Test
    void test_internal_PingDto_equals_and_hash_code() {

        //given
        PingDto pingDto1 = new PingDto();
        PingDto pingDto2 = PingDto.builder().code(200).message("SUCCESS").build();

        //when
        pingDto1.setCode(200);
        pingDto1.setMessage("SUCCESS");

        //then
        then(pingDto1).isEqualTo(pingDto2);
        then(pingDto1.equals(pingDto2)).isTrue();
        then(pingDto1.hashCode()).isEqualTo(pingDto2.hashCode());
    }

    @DisplayName("Test internal Dto -> PingDto --> toString")
    @Test
    void test_internal_PingDto_toString() {

        //given
        PingDto pingDto = PingDto.builder().code(200).message("SUCCESS").build();

        //when //then
        then(pingDto.toString()).isEqualTo("PingDto(code=200, message=SUCCESS)");
    }
}
package com.nia.assessment.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;


class ResponseDtoTest {

    @Test
    @DisplayName("Creating a ResponseDto should contain required fields")
    void test_create_ResponseDto_should_contain_fields() {

        //given
        ResponseDto responseDto = ResponseDto.builder()
                .status(100).message("SUCCESS").payload("Random payload here").build();

        //when //then
        then(responseDto).isNotNull();
        then(responseDto.getStatus()).isEqualTo(100);
        then(responseDto.getMessage()).isEqualTo("SUCCESS");
        then(responseDto.getPayload()).isEqualTo("Random payload here");
    }

    @Test
    @DisplayName("Creating 2 ResponseDto's should be equal")
    void test_create_2_ResponseDto_should_be_equal() {

        //given
        ResponseDto responseDto_01 = ResponseDto.builder()
                .status(100).message("SUCCESS").payload("Random payload here").build();
        ResponseDto responseDto_02 = ResponseDto.builder()
                .status(100).message("SUCCESS").payload("Random payload here").build();

        //when //then
        then(responseDto_01).isEqualTo(responseDto_02);
        then(responseDto_01.hashCode()).isEqualTo(responseDto_02.hashCode());
        then(responseDto_01.toString()).isEqualTo(responseDto_02.toString());
        then(responseDto_01.equals(responseDto_02)).isTrue();
    }

    @Test
    @DisplayName("Creating 2 ResponseDto's should not be equal if one has null payload")
    void test_create_2_ResponseDto_should_not_be_equal_if_one_has_null_payload() {

        //given
        ResponseDto responseDto_01 = ResponseDto.builder()
                .status(100).message("SUCCESS").payload("Random payload here").build();
        ResponseDto responseDto_02 = ResponseDto.builder()
                .status(100).message("SUCCESS").payload(null).build();

        //when //then
        then(responseDto_01).isNotEqualTo(responseDto_02);
        then(responseDto_01.hashCode()).isNotEqualTo(responseDto_02.hashCode());
        then(responseDto_01.toString()).isNotEqualTo(responseDto_02.toString());
        then(responseDto_01.equals(responseDto_02)).isFalse();

        //given
        responseDto_01 = ResponseDto.builder()
                .status(100).message("SUCCESS").payload(null).build();
        responseDto_02 = ResponseDto.builder()
                .status(100).message("SUCCESS").payload("Random payload here").build();

        //when //then
        then(responseDto_01).isNotEqualTo(responseDto_02);
        then(responseDto_01.hashCode()).isNotEqualTo(responseDto_02.hashCode());
        then(responseDto_01.toString()).isNotEqualTo(responseDto_02.toString());
        then(responseDto_01.equals(responseDto_02)).isFalse();
    }

    @Test
    @DisplayName("Creating an empty ResponseDto should have empty fields")
    void test_ResponseDto_default_constructor_no_args() {

        //given
        ResponseDto responseDto_01 = new ResponseDto();

        //when //then
        then(responseDto_01.getStatus()).isEqualTo(200);
        then(responseDto_01.getMessage()).isEqualTo("SUCCESS");
        then(responseDto_01.getPayload()).isNull();

        then(new ResponseDto()).isEqualTo(new ResponseDto());
    }

    @Test
    @DisplayName("Testing that setters and getters of ResponseDto are working fine")
    void test_ResponseDto_setters_and_getters_should_set_and_get_right_values() {

        //given
        ResponseDto responseDto_01 = new ResponseDto();

        //when
        responseDto_01.setStatus(400);
        responseDto_01.setMessage("ERROR");
        responseDto_01.setPayload("Some payload");

        //then
        then(responseDto_01.getStatus()).isEqualTo(400);
        then(responseDto_01.getMessage()).isEqualTo("ERROR");
        then(responseDto_01.getPayload()).isEqualTo("Some payload");
    }
}
package com.nia.assessment.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

class TokenDtoTest {

    @DisplayName("Test TokenDto getters and setters")
    @Test
    void test_UserDto_setters_and_getters() {

        //given
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccess_token("access_token");
        tokenDto.setRefresh_token("refresh_token");

        //when //then
        assertThat(tokenDto.getAccess_token().equals("access_token")).isTrue();
        assertThat(tokenDto.getRefresh_token().equals("refresh_token")).isTrue();
    }

    @DisplayName("Test TokenDto equals and hashCode")
    @Test
    void test_UserDto_equals_and_hashCode() {

        //given
        TokenDto tokenDto1 = TokenDto.builder().access_token("access_token").refresh_token("refresh_token").build();
        TokenDto tokenDto2 = TokenDto.builder().access_token("access_token").refresh_token("refresh_token").build();

        //when //then
        then(tokenDto1).isEqualTo(tokenDto2);
        then(tokenDto1.equals(tokenDto2)).isTrue();
        then(tokenDto1.hashCode()).isEqualTo(tokenDto2.hashCode());
    }


}
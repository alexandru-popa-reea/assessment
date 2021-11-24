package com.nia.assessment.controller;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

class UsersControllerTest {

    @DisplayName("Test UserDto getters and setters")
    @Test
    void test_UserDto_setters_and_getters() {

        //given
        UserDto userDto = new UserDto();
        userDto.setUsername("userName");
        userDto.setPassword("userPassword");

        //when //then
        assertThat(userDto.getUsername().equals("userName")).isTrue();
        assertThat(userDto.getPassword().equals("userPassword")).isTrue();
    }

    @DisplayName("Test UserDto equals and hashCode")
    @Test
    void test_UserDto_equals_and_hashCode() {

        //given
        UserDto userDto1 = UserDto.builder().username("userName").password("userPassword").build();
        UserDto userDto2 = UserDto.builder().username("userName").password("userPassword").build();

        //when //then
        then(userDto1).isEqualTo(userDto2);
        then(userDto1.equals(userDto2)).isTrue();
        then(userDto1.hashCode()).isEqualTo(userDto2.hashCode());
    }

}
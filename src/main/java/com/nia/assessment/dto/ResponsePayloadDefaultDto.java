package com.nia.assessment.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ResponsePayloadDefaultDto {

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        int length = ResponsePayloadDefaultDto.class.getDeclaredFields().length;
        return length == 0 && obj instanceof ResponsePayloadDefaultDto;
    }
}

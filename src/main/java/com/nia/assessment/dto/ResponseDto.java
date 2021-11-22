package com.nia.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {

    private int status;
    private String message;
    private Object payload;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseDto)) return false;
        ResponseDto that = (ResponseDto) o;
        return status == that.status && message.equals(that.message) && payload.equals(that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, payload);
    }
}
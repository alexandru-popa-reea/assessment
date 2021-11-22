package com.nia.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
public class ResponseDto {

    private int status;
    private String message;
    private Object payload;

    public ResponseDto() {
        this.payload = new ResponsePayloadDefaultDto();
    }

    public ResponseDto(int status, String message){
        this.status=status;
        this.message=message;
        this.payload = new ResponsePayloadDefaultDto();
    }

    public ResponseDto(int status, String message, Object payload) {
        this.status = status;
        this.message = message;
        this.payload = Objects.requireNonNullElseGet(payload, ResponsePayloadDefaultDto::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseDto)) return false;
        ResponseDto that = (ResponseDto) o;
        if (payload != null) {
            return status == that.status && message.equals(that.message) && payload.equals(that.payload);
        } else if (that.payload == null) {
            return status == that.status && message.equals(that.message);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, payload);
    }
}
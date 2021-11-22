package com.nia.assessment.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PingController {

    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public PingDto ping() {
        return PingDto.builder().code(200).message("Application is running").build();
    }

}

@Data
@Builder
class PingDto {
    private int code;
    private String message;
}
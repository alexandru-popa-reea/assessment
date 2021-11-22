package com.nia.assessment.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nia.assessment.dto.ResponseDto;
import com.nia.assessment.dto.TokenDto;
import com.nia.assessment.model.UserEntity;
import com.nia.assessment.service.TokenService;
import com.nia.assessment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api")
@Api(tags = "Authentication and authorization")
public class TokenController {


    @Value("${token.secret}")
    private String secret;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Autowired
    public TokenController(UserService userService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "This method is used to obtain a new access token (using the refresh token).")
    @GetMapping("/token/refresh")
    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                boolean refresh = decodedJWT.getClaim("refresh").asBoolean();
                if (!refresh) {
                    response.setHeader("ERROR", "Invalid refresh token");
                    ResponseDto responseDto = new ResponseDto(FORBIDDEN.value(), "The call was not made with a valid refresh token");
                    return ResponseEntity.status(FORBIDDEN.value()).body(responseDto);
                }
                UserEntity userEntity = userService.getUser(username);
                String access_token = tokenService.getAccessToken(userEntity, request.getRequestURL().toString());
                refresh_token = tokenService.getRefreshToken(userEntity, request.getRequestURL().toString());

                TokenDto tokenDto = new TokenDto(access_token, refresh_token);
                ResponseDto responseDto = new ResponseDto(OK.value(), "SUCCESS");
                responseDto.setPayload(tokenDto);

                return ResponseEntity.ok(responseDto);
            } catch (Exception exception) {
                String err = exception.getMessage();
                response.setHeader("ERROR", err);
                ResponseDto responseDto = new ResponseDto(INTERNAL_SERVER_ERROR.value(), err);
                return ResponseEntity.status(INTERNAL_SERVER_ERROR.value()).body(responseDto);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @ApiOperation(value = "This method is used to generate both access and refresh tokens.")
    @PostMapping(value = "/token/generate", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @SuppressWarnings("rawtypes")
    public ResponseEntity generateToken(@RequestBody UserDto user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            org.springframework.security.core.userdetails.User usr = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String access_token = tokenService.getAccessToken(usr, request.getRequestURL().toString());
            String refresh_token = tokenService.getRefreshToken(usr, request.getRequestURL().toString());

            ResponseDto responseDto = new ResponseDto(OK.value(), "SUCCESS");
            TokenDto tokenDto = new TokenDto(access_token, refresh_token);
            responseDto.setPayload(tokenDto);

            return ResponseEntity.ok(responseDto);
        } catch (AuthenticationException authenticationException) {
            ResponseDto responseDto = new ResponseDto(FORBIDDEN.value(), authenticationException.getMessage());
            return ResponseEntity.status(FORBIDDEN.value()).body(responseDto);
        } catch (Exception exception) {
            ResponseDto responseDto = new ResponseDto(INTERNAL_SERVER_ERROR.value(), exception.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR.value()).body(responseDto);
        }
    }
}

@Data
class UserDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}

package com.nia.assessment.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nia.assessment.dto.ResponseDto;
import com.nia.assessment.dto.TokenDto;
import com.nia.assessment.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        String access_token = tokenService.getAccessToken(user, request.getRequestURL().toString());
        String refresh_token = tokenService.getRefreshToken(user, request.getRequestURL().toString());

        TokenDto tokenDto = new TokenDto(access_token, refresh_token);
        ResponseDto responseDto = new ResponseDto(OK.value(), "SUCCESS", null);
        responseDto.setPayload(tokenDto);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseDto);
    }
}
package com.nia.assessment.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nia.assessment.dto.ResponseDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final String secret;

    public CustomAuthorizationFilter(String secret) {
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh") || request.getServletPath().startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    request.setAttribute("username", username);
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    if (roles == null) {
                        response.setHeader("ERROR", "Invalid access token");
                        response.setStatus(FORBIDDEN.value());
                        ResponseDto responseDto = new ResponseDto(FORBIDDEN.value(), "The call was not made with a valid access token", null);
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), responseDto);
                        return;
                    }
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (JWTVerificationException exception) {
                    String err = exception.getMessage();
                    response.setHeader("ERROR", err);
                    response.setStatus(FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);

                    ResponseDto responseDto = new ResponseDto(FORBIDDEN.value(), err, null);

                    new ObjectMapper().writeValue(response.getOutputStream(), responseDto);
                } catch (Exception e) {
                    String err = e.getMessage();
                    response.setHeader("ERROR", err);
                    response.setStatus(INTERNAL_SERVER_ERROR.value());
                    response.setContentType(APPLICATION_JSON_VALUE);

                    ResponseDto responseDto = new ResponseDto(INTERNAL_SERVER_ERROR.value(), err, null);

                    new ObjectMapper().writeValue(response.getOutputStream(), responseDto);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
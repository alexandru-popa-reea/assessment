package com.nia.assessment.configuration;


import com.nia.assessment.filter.CustomAuthenticationFilter;
import com.nia.assessment.filter.CustomAuthorizationFilter;
import com.nia.assessment.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    public SecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoder bCryptPasswordEncoder, TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and().csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(STATELESS);
        httpSecurity.authorizeRequests().antMatchers(
                "/api/login/**",
                "/api/token/generate/**",
                "/api/token/refresh/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v2/api-docs",
                "/webjars/**").permitAll();
        httpSecurity.authorizeRequests().
                antMatchers("/", "/index.html", "/static/**", "/favicon.ico", "/auth", "/login", "/public").
                permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/").permitAll();
        httpSecurity.authorizeRequests().anyRequest().permitAll();

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), tokenService);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        httpSecurity.addFilter(customAuthenticationFilter);
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(tokenService.getSecret()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.
                applyPermitDefaultValues().
                setAllowedMethods(List.of(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.HEAD.name())
                );
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
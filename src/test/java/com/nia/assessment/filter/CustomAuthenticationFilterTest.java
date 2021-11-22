package com.nia.assessment.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationFilterTest {

    @InjectMocks
    private CustomAuthenticationFilter customAuthenticationFilter;

    @Mock
    private AuthenticationManager authenticationManager;

    @DisplayName("Test authentication filter: ATTEMPT_AUTHENTICATION method should call authenticationManager-->authenticate() method")
    @Test
    void test_authentication_filter_ATTEMPT_AUTHENTICATION_should_call_authenticationManager() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("username", "jim");
        request.setParameter("password", "1234");

        customAuthenticationFilter.attemptAuthentication(request, response);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

}
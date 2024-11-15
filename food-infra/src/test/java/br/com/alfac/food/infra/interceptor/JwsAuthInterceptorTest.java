package br.com.alfac.food.infra.interceptor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import br.com.alfac.food.infra.helper.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwsAuthInterceptorTest {
    private JwtAuthInterceptor jwtAuthInterceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Object handler;
    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ3aG8iOiJBVVRIRU5USUNBVEVEIn0.4g5m5Q5";

    @BeforeEach
    public void setUp() {
        jwtAuthInterceptor = new JwtAuthInterceptor();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        handler = new Object();
    }

    @Test
    public void testPreHandleWithInvalidToken() {
        try (MockedStatic<JwtHelper> mockedJwtHelper = mockStatic(JwtHelper.class)) {
            when(request.getHeader("auth")).thenReturn("Bearer invalidToken");
            mockedJwtHelper.when(() -> JwtHelper.getWho("invalidToken")).thenReturn("INVALID");

            boolean result = jwtAuthInterceptor.preHandle(request, response, handler);

            assertFalse(result);
            verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Test
    public void testPreHandleWithNoToken() {
        when(request.getHeader("auth")).thenReturn("");

        boolean result = jwtAuthInterceptor.preHandle(request, response, handler);

        assertFalse(result);
        verify(response).setStatus(401);
    }

    @Test
    public void testPreHandleWithMalformedToken() {
        when(request.getHeader("auth")).thenReturn("InvalidTokenFormat");

        boolean result = jwtAuthInterceptor.preHandle(request, response, handler);

        assertFalse(result);
        verify(response).setStatus(401);
    }
}

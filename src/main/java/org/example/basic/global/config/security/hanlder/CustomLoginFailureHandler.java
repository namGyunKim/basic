package org.example.basic.global.config.security.hanlder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;

        if (exception instanceof BadCredentialsException) {
            errorMessage = exception.getMessage();
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = exception.getMessage();
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = exception.getMessage();
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = exception.getMessage();
        } else if (exception instanceof DisabledException) {
            errorMessage = exception.getMessage();
        } else if (exception instanceof LockedException) {
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "unknown issue";
        }
        errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}

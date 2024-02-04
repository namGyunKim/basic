package org.example.basic.global.error.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class EmailNotVerifiedException extends AuthenticationException {

    private final String message;

    public EmailNotVerifiedException(String message) {
        super(message);
        this.message = message;
    }
}

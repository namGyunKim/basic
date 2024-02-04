package org.example.basic.global.error;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.example.basic.global.error.exception.BusinessException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BindException.class})
    protected String handleBindException(BindException bindException) {
        log.error("handleBindException", bindException);
        return "error/404";
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        log.error("handleMethodArgumentTypeMismatchException", methodArgumentTypeMismatchException);
        return "error/404";
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    protected String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        log.error("handleHttpRequestMethodNotSupportedException", httpRequestMethodNotSupportedException);
        return "error/404";
    }

    @ExceptionHandler(value = {BusinessException.class})
    protected String handleConflict(BusinessException businessException) {
        log.error("BusinessException", businessException);
        return "error/404";
    }

    @ExceptionHandler(value = {Exception.class})
    protected String handleException(Exception exception) {
        log.error("Exception", exception);
        return "error/404";
    }

    @ExceptionHandler(value = {MessagingException.class})
    protected String handleMessagingException(MessagingException messagingException) {
        log.error("failed to send email", messagingException);
        return "error/404";
    }
}
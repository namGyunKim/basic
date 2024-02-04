package org.example.basic.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //    Common
    WRONG_REQUEST(HttpStatus.BAD_REQUEST, "C-001", "Bad Request"),
    JSON_PROCESS_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "C-002", "Json Process Fail"),
    REQUEST_BINDING_RESULT(HttpStatus.BAD_REQUEST, "C-003", "Request Binding Result"),
    ;


    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;


}

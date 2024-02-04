package org.example.basic.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //    Common
    WRONG_REQUEST(HttpStatus.BAD_REQUEST, "C-001", "Bad Request");


    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}

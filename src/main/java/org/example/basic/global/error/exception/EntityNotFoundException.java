package org.example.basic.global.error.exception;


import lombok.Getter;
import org.example.basic.global.error.ErrorCode;

import java.io.PrintWriter;
import java.io.StringWriter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorDetailMessage;

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // RuntimeException에 에러 메시지를 전달
        this.errorCode = errorCode;
        this.errorDetailMessage = getStackTraceMessage(this);
    }

    public EntityNotFoundException(ErrorCode errorCode, String errorDetailMessage) {
        super(errorDetailMessage); // RuntimeException에 에러 메시지를 전달
        this.errorCode = errorCode;
        this.errorDetailMessage = errorDetailMessage;
    }

    public EntityNotFoundException(ErrorCode errorCode, Exception exception) {
        super(errorCode.getMessage()); // RuntimeException에 에러 메시지를 전달
        this.errorCode = errorCode;
        this.errorDetailMessage = getStackTraceMessage(exception);
    }

    private String getStackTraceMessage(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

}

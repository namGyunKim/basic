package org.example.basic.global.event.log;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.basic.domain.account.entity.Account;
import org.example.basic.global.api.payload.response.BindingResultResponse;
import org.example.basic.global.error.ErrorCode;
import org.example.basic.global.error.exception.BindingException;

import java.time.LocalDateTime;

/**
 * packageName      : hwatong.web.global.event.log
 * fileName         : ExceptionEvent
 * author           : ryan
 * date             : 2023/02/28
 * description      :
 * =====================================================
 * DATE               AUTHOR                NOTE
 * -----------------------------------------------------
 * 2023/02/28          ryan       최초 생성
 */
@Data
@NoArgsConstructor
public class ExceptionEvent {

    protected String requestPath;
    protected String requestMethod;
    protected String errorName;
    protected ErrorCode errorCode;
    protected String errorDetailMsg;
    protected LocalDateTime createdAt;
    protected Account account;

    //  예외 발생 시, 바인딩 리절트 에러 정보를 담는 이벤트 객체
    public static ExceptionEvent createExceptionEventBinding(BindingResultResponse response) {
        ExceptionEvent exceptionEvent = new ExceptionEvent();
        BindingException exception = new BindingException(ErrorCode.REQUEST_BINDING_RESULT);
        exceptionEvent.setRequestPath(response.path());
        exceptionEvent.setRequestMethod(response.method());
        exceptionEvent.setErrorName(exception.getClass().getSimpleName());
        exceptionEvent.setErrorCode(exception.getErrorCode());
        exceptionEvent.setErrorDetailMsg(response.content().toString());
        exceptionEvent.setCreatedAt(LocalDateTime.now());
        return exceptionEvent;
    }

    //  예외 발생 시, 이벤트 로그 생성
    public String getExceptionString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\nlogStart=== === === === === === === === === === === === === === === === === === === === === === === === === === logStart\n");
        stringBuilder.append("Exception Title : ").append(errorName).append("\n");


        // 1. Set Request Info
        stringBuilder.append("Request Path : ").append(requestPath).append("\n");
        stringBuilder.append("Request Method : ").append(requestMethod).append("\n");

        // 2. Set User Info
        if (account != null) {
            stringBuilder.append("Account role : ").append(account.getRole()).append("\n");
            stringBuilder.append("Account Username : ").append(account.getLoginId()).append("\n");
        }
        // 3. Set Exception
        if (this.errorCode != null) {
            stringBuilder.append("Error Code & Msg : ").append(errorCode.getErrorCode()).append(" / ").append(errorCode.getMessage()).append("\n");
        }

        // 4. Occur Date
        stringBuilder.append("createDate : ").append(createdAt.toString()).append("\n\n");


        // 5. Set Error Detail Msg
        stringBuilder.append(errorDetailMsg);
        stringBuilder.append("\nlogEnd=== === === === === === === === === === === === === === === === === === === === === === === === === === logEnd\n\n");

        return stringBuilder.toString();
    }

}

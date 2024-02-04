package org.example.basic.global.error.exception.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.basic.global.api.controller.RestApiController;
import org.example.basic.global.api.payload.response.BindingResultResponse;
import org.example.basic.global.event.log.ExceptionEvent;
import org.springframework.context.ApplicationEventPublisher;

// 예외 처리
public class RestApiControllerAdvice extends RestApiController {

    private final ApplicationEventPublisher applicationEventPublisher;


    public RestApiControllerAdvice(ObjectMapper objectMapper, ApplicationEventPublisher applicationEventPublisher) {
        super(objectMapper);
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // 컨트롤러를 거친 이후 Event - Log
//    protected void sendLogEvent(GlobalException exception, CurrentAccountDTO account, HttpServletRequest httpServletRequest) {
//        applicationEventPublisher.publishEvent(ExceptionEvent.createExceptionEvent(exception, account,httpServletRequest));
//    }

    // 컨트롤러를 거치기 전 JWT 관련 이슈가 터지면 error 컨트롤러로 보내서 해당 Event - Log
//    protected void sendLogEvent(JWTInterceptorException exception,HttpServletRequest httpServletRequest) {
//        applicationEventPublisher.publishEvent(ExceptionEvent.createExceptionEventNoAccount(exception,httpServletRequest));
//    }

    // 컨트롤러를 거치기 전 바인딩 리절트 관련 이슈가 터지면 error 컨트롤러로 보내서 해당 Event - Log
    protected void sendLogEvent(BindingResultResponse response) {
        applicationEventPublisher.publishEvent(ExceptionEvent.createExceptionEventBinding(response));
    }
}

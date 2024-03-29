package org.example.basic.global.error.exception.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.basic.global.api.payload.response.BindingResultResponse;
import org.example.basic.global.error.ErrorCode;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


// 바인딩 리절트 에러 핸들러
@Slf4j
@Aspect
@Component
public class BindingAdvice extends RestApiControllerAdvice{

    public BindingAdvice(ObjectMapper objectMapper, ApplicationEventPublisher applicationEventPublisher) {
        super(objectMapper, applicationEventPublisher);
    }

    @Around("execution(* org.example.basic ..*RestController.*(..))")
    public Object validationHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        String type = bindingPathCreate(joinPoint.getSignature());
        String method = "Not Found";
        String errorCode = ErrorCode.REQUEST_BINDING_RESULT.getErrorCode();
        String errorMessage = ErrorCode.REQUEST_BINDING_RESULT.getMessage();
        Map<String, String> errorMap = new HashMap<>();
        Object[] args = joinPoint.getArgs(); // join point parameter
        for (Object arg : args) {
//            바인딩 리절트가 존재하면
            if (arg instanceof BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                    populateErrorMap(bindingResult, errorMap);
                    BindingResultResponse response = new BindingResultResponse(false, type, method, errorCode, errorMessage, errorMap);
                    sendLogEvent(response);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }
        }

        return joinPoint.proceed();
    }

    private void populateErrorMap(BindingResult bindingResult, Map<String, String> errorMap) {
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
    }

    private String bindingPathCreate(Signature signature){
        String declaringTypeName = signature.getDeclaringTypeName();
        String name = signature.getName();
        String [] splitPath = declaringTypeName.split("\\.");
        StringBuilder stringBuilder = new StringBuilder(splitPath[splitPath.length-1]);
        stringBuilder.append(" ").append(name);
        return stringBuilder.toString();
    }
}
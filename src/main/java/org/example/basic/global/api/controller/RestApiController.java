package org.example.basic.global.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.basic.global.api.payload.response.RestApiResponse;
import org.example.basic.global.error.ErrorCode;
import org.example.basic.global.error.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

// 공통된 응답을 만들어주는 클래스
@Component
public class RestApiController {

    private final ObjectMapper objectMapper;

    public RestApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //    실패 응답
    public ResponseEntity<String> createFailRestResponse(Object data) {
        RestApiResponse restApiResponse = RestApiResponse.createResponse(false, data);
        return convertToResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, restApiResponse);
    }

    //    성공 응답
    public ResponseEntity<String> createRestResponse(Object data) {
        RestApiResponse restApiResponse = RestApiResponse.createResponse(true, data);
        return convertToResponseEntity(HttpStatus.OK, restApiResponse);
    }

    //    생성 성공 응답
    public ResponseEntity<String> createSuccessRestResponse(Object data) {
        RestApiResponse restApiResponse = RestApiResponse.createResponse(true, data);
        return convertToResponseEntity(HttpStatus.CREATED, restApiResponse);
    }

    //    응답 생성
    private ResponseEntity<String> convertToResponseEntity(HttpStatus status, RestApiResponse restApiResponse) {
        String responseBody;
        try {
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            responseBody = objectMapper.writeValueAsString(restApiResponse);
        } catch (JsonProcessingException exception) {
            throw new CommonException(ErrorCode.JSON_PROCESS_FAIL,exception);
        }
//        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(responseBody);
        return ResponseEntity.status(status).body(responseBody);
    }
}

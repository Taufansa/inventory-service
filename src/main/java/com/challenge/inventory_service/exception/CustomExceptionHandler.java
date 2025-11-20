package com.challenge.inventory_service.exception;

import com.challenge.inventory_service.dto.GeneralApiExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleApiGeneralException(GeneralException ex) {
        return ResponseEntity
            .status(ex.getStatusCode())
            .body(GeneralApiExceptionResponse.builder()
                .statusCode(ex.getStatusCode())   
                .responseCode(ex.getResponseCode()) 
                .message(ex.getMessage())
                .referenceNumber(ex.getReferenceNumber())
                .traceId(ex.getTraceId())
            .build());
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<Object> handleApiOrderException(GeneralException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(GeneralApiExceptionResponse.builder()
                        .statusCode(ex.getStatusCode())
                        .responseCode(ex.getResponseCode())
                        .message(ex.getMessage())
                        .referenceNumber(ex.getReferenceNumber())
                        .traceId(ex.getTraceId())
                        .build());
    }
    
}

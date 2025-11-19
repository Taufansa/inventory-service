package com.challenge.inventory_service.exception;


import com.challenge.inventory_service.constant.ResponseCodeConstants;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class GeneralException extends RuntimeException {
    private final int statusCode;
    private final String responseCode;
    private final String message;
    private final String referenceNumber;
    private final String traceId;
    

    public GeneralException(String message, String responseCode, int statusCode, String referenceNumber) {
        super(message);
        this.message = message;
        this.responseCode = responseCode;
        this.statusCode = statusCode;
        this.referenceNumber = referenceNumber;
        this.traceId = MDC.get("traceId");
    }

    public GeneralException(String message, String referenceNumber) {
        this.message = message;
        this.statusCode = HttpStatus.BAD_GATEWAY.value();
        this.responseCode = ResponseCodeConstants.RESPONSE_CODE_GENERAL_ERROR;
        this.referenceNumber = referenceNumber;
        this.traceId = MDC.get("traceId");
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package com.challenge.inventory_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralApiExceptionResponse {

    private final int statusCode;
    private final String responseCode;
    private final String message;
    private final String referenceNumber;
    private final String traceId;
    
}

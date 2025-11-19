package com.challenge.inventory_service.dto;

import com.challenge.inventory_service.constant.ResponseCodeConstants;
import lombok.Builder;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class BaseApiResponseDto {
    
    private int statusCode = HttpStatus.OK.value();
    private String responseCode = ResponseCodeConstants.RESPONSE_CODE_SUCCESS;
    private String message = ResponseCodeConstants.RESPONSE_DESC_SUCCESS;
    private String referenceNumber = null;
    private String traceId = MDC.get("traceId");
    
}

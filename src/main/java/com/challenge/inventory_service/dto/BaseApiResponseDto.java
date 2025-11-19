package com.challenge.inventory_service.dto;

import com.challenge.inventory_service.constant.ResponseCodeConstants;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public abstract class BaseApiResponseDto {
    
    private int statusCode = HttpStatus.OK.value();
    private String responseCode = ResponseCodeConstants.RESPONSE_CODE_SUCCESS;
    private String message = ResponseCodeConstants.RESPONSE_DESC_SUCCESS;
    private String referenceNumber;
    private String traceId = MDC.get("traceId");
    
}

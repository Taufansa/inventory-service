package com.challenge.inventory_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseApiRequestDto {

    private String referenceNumber;
    
}

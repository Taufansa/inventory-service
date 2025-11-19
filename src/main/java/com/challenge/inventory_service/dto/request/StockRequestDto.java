package com.challenge.inventory_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockRequestDto {

    private Long available;
    private Long book;
    private Long sold;

}

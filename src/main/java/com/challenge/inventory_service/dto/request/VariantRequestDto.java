package com.challenge.inventory_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariantRequestDto {

    private String variantName;
    private String variantColor;
    private String variantSize;
    private Integer variantWeight;
    private PriceRequestDto price;
    private StockRequestDto stock;

}

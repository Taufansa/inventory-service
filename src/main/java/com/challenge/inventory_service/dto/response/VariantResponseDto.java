package com.challenge.inventory_service.dto.response;

import com.challenge.inventory_service.model.Price;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.model.Variant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariantResponseDto {

    private Variant variant;
    private Price price;
    private Stock stock;

}

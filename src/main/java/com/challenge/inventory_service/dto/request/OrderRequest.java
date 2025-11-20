package com.challenge.inventory_service.dto.request;

import com.challenge.inventory_service.dto.BaseApiRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderRequest extends BaseApiRequestDto {

    private Long itemId;
    private Long variantId;
    private Long quantity;
    private String state;

}

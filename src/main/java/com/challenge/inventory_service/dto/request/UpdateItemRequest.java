package com.challenge.inventory_service.dto.request;

import com.challenge.inventory_service.dto.BaseApiRequestDto;
import com.challenge.inventory_service.dto.response.VariantResponseDto;
import com.challenge.inventory_service.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UpdateItemRequest extends BaseApiRequestDto {

    private Item item;
    private List<VariantResponseDto> variants;

}

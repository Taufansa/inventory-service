package com.challenge.inventory_service.dto.response;

import com.challenge.inventory_service.dto.BaseApiResponseDto;
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
public class CreateItemResponse extends BaseApiResponseDto {

    private Item item;
    private List<VariantResponseDto> variants;

}

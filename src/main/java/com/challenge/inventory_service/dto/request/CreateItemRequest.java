package com.challenge.inventory_service.dto.request;

import com.challenge.inventory_service.dto.BaseApiRequestDto;
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
public class CreateItemRequest extends BaseApiRequestDto {

    private ItemRequestDto item;
    private List<VariantRequestDto> variants;

}

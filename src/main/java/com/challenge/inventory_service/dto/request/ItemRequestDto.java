package com.challenge.inventory_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequestDto {

    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private String itemSku;

}

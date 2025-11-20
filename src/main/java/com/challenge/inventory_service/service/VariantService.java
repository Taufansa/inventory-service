package com.challenge.inventory_service.service;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.response.VariantResponseDto;
import com.challenge.inventory_service.model.Item;
import java.util.List;

public interface VariantService {

    List<VariantResponseDto> addVariant(CreateItemRequest request, Item item);
    List<VariantResponseDto> getVariantByItemId(Long itemId);

}

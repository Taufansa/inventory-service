package com.challenge.inventory_service.service;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.request.UpdateItemRequest;
import com.challenge.inventory_service.dto.response.ItemResponse;

public interface ItemService {

    ItemResponse createItem(CreateItemRequest createItemRequest);
    ItemResponse getItem(Long id);
    void deleteItem(Long id);
    ItemResponse updateItem(UpdateItemRequest updateItemRequest);

}

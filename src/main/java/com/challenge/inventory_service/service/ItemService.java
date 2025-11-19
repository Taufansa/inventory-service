package com.challenge.inventory_service.service;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.response.CreateItemResponse;

public interface ItemService {

    CreateItemResponse createItem(CreateItemRequest createItemRequest);

}

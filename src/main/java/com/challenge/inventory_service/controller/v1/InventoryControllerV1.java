package com.challenge.inventory_service.controller.v1;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.response.CreateItemResponse;
import com.challenge.inventory_service.service.ItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class InventoryControllerV1 {

    private final ItemService itemService;

    public InventoryControllerV1(ItemService itemService) {

        this.itemService = itemService;

    }

    @PostMapping("/save")
    public CreateItemResponse createItem(@RequestBody CreateItemRequest createItemRequest) {

        return itemService.createItem(createItemRequest);

    }

}

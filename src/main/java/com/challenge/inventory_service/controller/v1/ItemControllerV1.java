package com.challenge.inventory_service.controller.v1;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.response.ItemResponse;
import com.challenge.inventory_service.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/item")
public class ItemControllerV1 {

    private final ItemService itemService;

    public ItemControllerV1(ItemService itemService) {

        this.itemService = itemService;

    }

    @PostMapping("/save")
    public ItemResponse createItem(@RequestBody CreateItemRequest createItemRequest) {

        return itemService.createItem(createItemRequest);

    }

    @GetMapping("/{id}")
    public ItemResponse getItem(@PathVariable("id") Long itemId) {

        return itemService.getItem(itemId);

    }

}

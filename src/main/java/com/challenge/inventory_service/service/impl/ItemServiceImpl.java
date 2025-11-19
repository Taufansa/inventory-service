package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.response.CreateItemResponse;
import com.challenge.inventory_service.dto.response.VariantResponseDto;
import com.challenge.inventory_service.exception.GeneralException;
import com.challenge.inventory_service.model.Item;
import com.challenge.inventory_service.repository.ItemRepository;
import com.challenge.inventory_service.service.ItemService;
import com.challenge.inventory_service.service.VariantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final VariantService variantService;

    public ItemServiceImpl(ItemRepository itemRepository, VariantService variantService) {

        this.itemRepository = itemRepository;
        this.variantService = variantService;

    }

    @Transactional
    @Override
    public CreateItemResponse createItem(CreateItemRequest createItemRequest) {

        try {

            log.info("start create item with request: {}", createItemRequest);

            Item savedItem = itemRepository.saveAndFlush(Item.builder()
                            .itemName(createItemRequest.getItem().getItemName())
                            .itemDescription(createItemRequest.getItem().getItemDescription())
                            .itemCategory(createItemRequest.getItem().getItemCategory())
                            .itemSku(createItemRequest.getItem().getItemSku())
                    .build());

            List<VariantResponseDto> savedVariants = variantService.addVariant(createItemRequest, savedItem);

            return CreateItemResponse.builder()
                    .item(savedItem)
                    .variants(savedVariants)
                    .referenceNumber(createItemRequest.getReferenceNumber())
                    .build();

        } catch (Exception e) {

            log.error("error when create item with error: {}", e.getMessage());

            throw new GeneralException(e.getMessage(), createItemRequest.getReferenceNumber());

        }

    }
}

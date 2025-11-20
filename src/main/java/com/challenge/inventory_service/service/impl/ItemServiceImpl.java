package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.request.UpdateItemRequest;
import com.challenge.inventory_service.dto.response.ItemResponse;
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
import java.util.Optional;

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
    public ItemResponse createItem(CreateItemRequest createItemRequest) {

        try {

            log.info("start create item with request: {}", createItemRequest);

            Item savedItem = itemRepository.saveAndFlush(Item.builder()
                            .itemName(createItemRequest.getItem().getItemName())
                            .itemDescription(createItemRequest.getItem().getItemDescription())
                            .itemCategory(createItemRequest.getItem().getItemCategory())
                            .itemSku(createItemRequest.getItem().getItemSku())
                    .build());

            List<VariantResponseDto> savedVariants = variantService.addVariant(createItemRequest, savedItem);

            return ItemResponse.builder()
                    .item(savedItem)
                    .variants(savedVariants)
                    .referenceNumber(createItemRequest.getReferenceNumber())
                    .build();

        } catch (Exception e) {

            log.error("error when create item with error: {}", e.getMessage());

            throw new GeneralException(e.getMessage(), createItemRequest.getReferenceNumber());

        }

    }

    @Override
    public ItemResponse getItem(Long id) {

        try {

            log.info("start get item with id: {}", id);

            Optional<Item> item = itemRepository.findById(id);

            log.info("result getting item with id {} is {}", id, item);

            if (item.isEmpty()) {

                return ItemResponse.builder()
                        .item(null)
                        .variants(List.of())
                        .build();

            }

            return ItemResponse.builder()
                    .item(item.get())
                    .variants(variantService.getVariantByItemId(item.get().getId()))
                    .build();


        } catch (Exception e) {

            log.error("error when get item with id {} and error is {}", id, e.getMessage());

            throw new GeneralException(e.getMessage());

        }

    }

    @Transactional
    @Override
    public void deleteItem(Long id) {
        try {

            log.info("start delete item with id: {}", id);
            Optional<Item> item = itemRepository.findById(id);
            if (item.isPresent()) {
                variantService.deleteVariant(item.get().getId());
                itemRepository.delete(item.get());
            }
            log.info("result deleting item with id {} is {}", id, item);

        } catch (Exception e) {

            log.error("error when delete item with id {} and error is {}", id, e.getMessage());
            throw new GeneralException(e.getMessage());

        }
    }

    @Override
    public ItemResponse updateItem(UpdateItemRequest updateItemRequest) {

        try {

            log.info("start update item with request: {}", updateItemRequest);
            Item updatedItem = itemRepository.saveAndFlush(updateItemRequest.getItem());
            log.info("result updating item with id {} is {}", updatedItem.getId(), updatedItem);
            variantService.updateVariant(updateItemRequest);

            return getItem(updateItemRequest.getItem().getId());

        } catch (Exception e) {

            log.error("error when update item with request {} and got error {}", updateItemRequest, e.getMessage());
            throw new GeneralException(e.getMessage());

        }

    }
}

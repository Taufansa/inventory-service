package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.request.ItemRequestDto;
import com.challenge.inventory_service.dto.request.PriceRequestDto;
import com.challenge.inventory_service.dto.request.StockRequestDto;
import com.challenge.inventory_service.dto.request.VariantRequestDto;
import com.challenge.inventory_service.dto.response.ItemResponse;
import com.challenge.inventory_service.dto.response.VariantResponseDto;
import com.challenge.inventory_service.model.Item;
import com.challenge.inventory_service.model.Price;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.model.Variant;
import com.challenge.inventory_service.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private VariantServiceImpl variantService;

    @Test
    void successCreateItem() {

        CreateItemRequest request = CreateItemRequest.builder()
                .item(ItemRequestDto.builder()
                        .itemName("T-Shirt Nike")
                        .itemDescription("Pakaian Santai Pria")
                        .itemCategory("Pakaian Pria")
                        .itemSku("1910MSOUUN")
                        .build())
                .variants(List.of(VariantRequestDto.builder()
                                .variantName("Kobe Bryant 24")
                                .variantColor("Black")
                                .variantSize("XL")
                                .variantWeight(100)
                                .price(PriceRequestDto.builder()
                                        .price(BigDecimal.valueOf(150000L))
                                        .currency("IDR")
                                        .build())
                                .stock(StockRequestDto.builder()
                                        .available(100L)
                                        .book(0L)
                                        .sold(0L)
                                        .build())
                                .build(),
                        VariantRequestDto.builder()
                                .variantName("Kobe Bryant 24")
                                .variantColor("White")
                                .variantSize("XXL")
                                .variantWeight(100)
                                .price(PriceRequestDto.builder()
                                        .price(BigDecimal.valueOf(150000L))
                                        .currency("IDR")
                                        .build())
                                .stock(StockRequestDto.builder()
                                        .available(100L)
                                        .book(0L)
                                        .sold(0L)
                                        .build())
                                .build()))
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        Item item = Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("Pakaian Santai Pria").itemCategory("Pakaian Pria").itemSku("1910MSOUUN").createdAt(new Date()).createdBy("ADMIN").build();

        Mockito.when(itemRepository.saveAndFlush(ArgumentMatchers.any(Item.class))).thenReturn(item);
        Mockito.when(variantService.addVariant(ArgumentMatchers.any(CreateItemRequest.class), ArgumentMatchers.any(Item.class))).thenReturn(
                List.of(
                        VariantResponseDto.builder().variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantColor("Black").variantSize("XL").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()).price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()).stock(Stock.builder().id(1L).variantId(1L).available(100L).book(0L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build()).build(),
                        VariantResponseDto.builder().variant(Variant.builder().id(2L).itemId(1L).variantName("Kobe Bryant 24").variantColor("White").variantSize("XXL").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()).price(Price.builder().id(2L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()).stock(Stock.builder().id(2L).variantId(1L).available(100L).book(0L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build()).build()
                ));

        ItemResponse result = itemService.createItem(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getReferenceNumber(), result.getReferenceNumber());
        Assertions.assertEquals(request.getItem().getItemName(), result.getItem().getItemName());
        Assertions.assertEquals(request.getItem().getItemCategory(), result.getItem().getItemCategory());
        Assertions.assertEquals(request.getItem().getItemSku(), result.getItem().getItemSku());
        Assertions.assertEquals(request.getItem().getItemDescription(), result.getItem().getItemDescription());
        Assertions.assertEquals(2, result.getVariants().size());

    }

    @Test
    void failedCreateItem() {

        CreateItemRequest request = CreateItemRequest.builder()
                .item(ItemRequestDto.builder()
                        .itemName("T-Shirt Nike")
                        .itemDescription("Pakaian Santai Pria")
                        .itemCategory("Pakaian Pria")
                        .itemSku("1910MSOUUN")
                        .build())
                .variants(List.of(VariantRequestDto.builder()
                                .variantName("Kobe Bryant 24")
                                .variantColor("Black")
                                .variantSize("XL")
                                .variantWeight(100)
                                .price(PriceRequestDto.builder()
                                        .price(BigDecimal.valueOf(150000L))
                                        .currency("IDR")
                                        .build())
                                .stock(StockRequestDto.builder()
                                        .available(100L)
                                        .book(0L)
                                        .sold(0L)
                                        .build())
                                .build(),
                        VariantRequestDto.builder()
                                .variantName("Kobe Bryant 24")
                                .variantColor("White")
                                .variantSize("XXL")
                                .variantWeight(100)
                                .price(PriceRequestDto.builder()
                                        .price(BigDecimal.valueOf(150000L))
                                        .currency("IDR")
                                        .build())
                                .stock(StockRequestDto.builder()
                                        .available(100L)
                                        .book(0L)
                                        .sold(0L)
                                        .build())
                                .build()))
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        Mockito.when(itemRepository.saveAndFlush(ArgumentMatchers.any(Item.class))).thenThrow(RuntimeException.class);

        Exception e = null;

        try {

            itemService.createItem(request);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);

    }

    @Test
    void successGetItem() {

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("T-Shirt dengan bahan kualitas baik").itemCategory("Pakaian Pria").itemSku("1872GNC0").createdAt(new Date()).createdBy("ADMIN").build()));
        Mockito.when(variantService.getVariantByItemId(1L)).thenReturn(List.of(VariantResponseDto.builder().variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()).stock(Stock.builder().id(1L).variantId(1L).available(100L).book(0L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build()).price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()).build()));

        ItemResponse result = itemService.getItem(1L);

        Assertions.assertNotNull(result.getItem());
        Assertions.assertEquals(1, result.getVariants().size());

    }

    @Test
    void successGetItemButWithEmptyResult() {

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        ItemResponse result = itemService.getItem(1L);

        Assertions.assertNull(result.getItem());
        Assertions.assertEquals(0, result.getVariants().size());

    }

    @Test
    void failedGetItem() {

        Mockito.when(itemRepository.findById(1L)).thenThrow(RuntimeException.class);

        Exception e = null;

        try {

            itemService.getItem(1L);

        }  catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);

    }
}
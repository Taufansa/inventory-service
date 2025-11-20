package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.request.ItemRequestDto;
import com.challenge.inventory_service.dto.request.PriceRequestDto;
import com.challenge.inventory_service.dto.request.StockRequestDto;
import com.challenge.inventory_service.dto.request.VariantRequestDto;
import com.challenge.inventory_service.dto.response.VariantResponseDto;
import com.challenge.inventory_service.model.Item;
import com.challenge.inventory_service.model.Price;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.model.Variant;
import com.challenge.inventory_service.repository.ItemRepository;
import com.challenge.inventory_service.repository.VariantRepository;
import com.challenge.inventory_service.service.StockService;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VariantServiceImplTest {

    @InjectMocks
    private VariantServiceImpl variantService;

    @Mock
    private StockServiceImpl stockService;

    @Mock
    private PriceServiceImpl priceService;

    @Mock
    private VariantRepository variantRepository;

    @Test
    void successAddVariant() {

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
        Item item = Item.builder().id(1L).itemName("T-Shirt Nike").itemCategory("Pakaian Pria").itemDescription("Pakaian Santai Pria").itemSku("1910MSOUUN").createdAt(new Date()).createdBy("ADMIN").build();

        Mockito.when(variantRepository.saveAndFlush(ArgumentMatchers.any(Variant.class))).thenReturn(Variant.builder().id(1L).variantName("T-Shirt Nike").variantWeight(100).variantColor("Black").variantSize("XL").itemId(1L).build());
        Mockito.when(priceService.savePrice(ArgumentMatchers.any(PriceRequestDto.class), ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(Price.builder().id(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").build());
        Mockito.when(stockService.saveStock(ArgumentMatchers.any(StockRequestDto.class), ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(Stock.builder().id(1L).variantId(1L).available(100L).book(0L).sold(0L).build());

        List<VariantResponseDto> result = variantService.addVariant(request, item);

        Assertions.assertEquals(2, result.size());

    }

    @Test
    void failedAddVariant() {

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
        Item item = Item.builder().id(1L).itemName("T-Shirt Nike").itemCategory("Pakaian Pria").itemDescription("Pakaian Santai Pria").itemSku("1910MSOUUN").createdAt(new Date()).createdBy("ADMIN").build();

        Mockito.when(variantRepository.saveAndFlush(ArgumentMatchers.any(Variant.class))).thenThrow(RuntimeException.class);

        Exception e = null;

        try {

            variantService.addVariant(request, item);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);
    }

    @Test
    void successGetVariantByItemId() {

        Mockito.when(variantRepository.findAllByItemId(Mockito.anyLong())).thenReturn(List.of(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()));
        Mockito.when(priceService.getPrice(ArgumentMatchers.anyLong())).thenReturn(Optional.of(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()));
        Mockito.when(stockService.getStock(ArgumentMatchers.anyLong())).thenReturn(Optional.of(Stock.builder().id(1L).variantId(1L).available(100L).book(0L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build()));

        List<VariantResponseDto> result = variantService.getVariantByItemId(1L);

        Assertions.assertEquals(1, result.size());

    }

    @Test
    void successGetVariantByItemIdButReturnEmpty() {

        Mockito.when(variantRepository.findAllByItemId(Mockito.anyLong())).thenReturn(List.of());

        List<VariantResponseDto> result = variantService.getVariantByItemId(1L);

        Assertions.assertEquals(0, result.size());

    }

    @Test
    void failedGetVariantByItemId() {

        Mockito.when(variantRepository.findAllByItemId(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Exception e = null;

        try {

            variantService.getVariantByItemId(1L);

        } catch (Exception ex) {
            e = ex;
        }

        Assertions.assertNotNull(e);

    }
}
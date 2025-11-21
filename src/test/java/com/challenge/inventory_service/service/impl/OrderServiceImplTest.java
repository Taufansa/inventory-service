package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.constant.ResponseCodeConstants;
import com.challenge.inventory_service.dto.request.OrderRequest;
import com.challenge.inventory_service.dto.response.ItemResponse;
import com.challenge.inventory_service.dto.response.VariantResponseDto;
import com.challenge.inventory_service.model.Item;
import com.challenge.inventory_service.model.Price;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.model.Variant;
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
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private ItemServiceImpl itemService;

    @Mock
    private StockServiceImpl stockService;

    @Test
    void successProcessOrderWhenStateIsBook() {

        OrderRequest request = OrderRequest.builder()
                .itemId(1L)
                .variantId(1L)
                .quantity(2L)
                .state("BOOK")
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        ItemResponse itemResponse = ItemResponse.builder()
                .item(Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("T-Shirt dengan bahan kualitas baik").itemCategory("Pakaian Pria").itemSku("1872GNC0").createdAt(new Date()).createdBy("ADMIN").build())
                .variants(List.of(VariantResponseDto.builder().variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()).stock(Stock.builder().id(1L).variantId(1L).available(100L).book(0L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build()).price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()).build()))
                .build();

        ItemResponse itemResponseAfterBook = ItemResponse.builder()
                .item(Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("T-Shirt dengan bahan kualitas baik").itemCategory("Pakaian Pria").itemSku("1872GNC0").createdAt(new Date()).createdBy("ADMIN").build())
                .variants(List.of(VariantResponseDto.builder().variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()).stock(Stock.builder().id(1L).variantId(1L).available(98L).book(2L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build()).price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()).build()))
                .build();

        Mockito.when(itemService.getItem(ArgumentMatchers.anyLong())).thenReturn(itemResponse).thenReturn(itemResponseAfterBook);
        Mockito.when(stockService.updateStock(ArgumentMatchers.any(Stock.class), ArgumentMatchers.anyString())).thenReturn(Stock.builder().id(1L).variantId(1L).available(98L).book(2L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build());

        ItemResponse result = orderService.processOrder(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(itemResponseAfterBook, result);

    }

    @Test
    void successProcessOrderWhenStateIsPayment() {

        OrderRequest request = OrderRequest.builder()
                .itemId(1L)
                .variantId(1L)
                .quantity(2L)
                .state("Payment")
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        ItemResponse itemResponse = ItemResponse.builder()
                .item(Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("T-Shirt dengan bahan kualitas baik").itemCategory("Pakaian Pria").itemSku("1872GNC0").createdAt(new Date()).createdBy("ADMIN").build())
                .variants(List.of(
                        VariantResponseDto.builder()
                                .variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build())
                                .stock(Stock.builder().id(1L).variantId(1L).available(98L).book(2L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build())
                                .price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build())
                                .build())
                )
                .build();

        ItemResponse itemResponseAfterPayment = ItemResponse.builder()
                .item(Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("T-Shirt dengan bahan kualitas baik").itemCategory("Pakaian Pria").itemSku("1872GNC0").createdAt(new Date()).createdBy("ADMIN").build())
                .variants(List.of(VariantResponseDto.builder().variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()).stock(Stock.builder().id(1L).variantId(1L).available(98L).book(0L).sold(2L).createdAt(new Date()).createdBy("ADMIN").build()).price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()).build()))
                .build();

        Mockito.when(itemService.getItem(ArgumentMatchers.anyLong())).thenReturn(itemResponse).thenReturn(itemResponseAfterPayment);
        Mockito.when(stockService.updateStock(ArgumentMatchers.any(Stock.class), ArgumentMatchers.anyString())).thenReturn(Stock.builder().id(1L).variantId(1L).available(98L).book(0L).sold(2L).createdAt(new Date()).createdBy("ADMIN").build());

        ItemResponse result = orderService.processOrder(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(itemResponseAfterPayment, result);

    }

    @Test
    void failedProcessOrder() {

        OrderRequest request = OrderRequest.builder()
                .itemId(1L)
                .variantId(1L)
                .quantity(2L)
                .state("Payment")
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        Mockito.when(itemService.getItem(ArgumentMatchers.anyLong())).thenThrow(RuntimeException.class);

        Exception e = null;

        try {

            orderService.processOrder(request);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);

    }

    @Test
    void failedProcessOrderWhenItemNotFound() {

        OrderRequest request = OrderRequest.builder()
                .itemId(1L)
                .variantId(1L)
                .quantity(2L)
                .state("Payment")
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        ItemResponse itemResponse = ItemResponse.builder()
                .item(null)
                .variants(List.of())
                .build();

        Mockito.when(itemService.getItem(ArgumentMatchers.anyLong())).thenReturn(itemResponse);

        Exception e = null;

        try {

            orderService.processOrder(request);

        }  catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);
        Assertions.assertEquals(ResponseCodeConstants.RESPONSE_DESC_ORDER_ITEM_NOT_FOUND, e.getMessage());

    }

    @Test
    void failedProcessOrderWhenOrderBookAndStockNotAvailable() {

        OrderRequest request = OrderRequest.builder()
                .itemId(1L)
                .variantId(1L)
                .quantity(2L)
                .state("BOOK")
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        ItemResponse itemResponse = ItemResponse.builder()
                .item(Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("T-Shirt dengan bahan kualitas baik").itemCategory("Pakaian Pria").itemSku("1872GNC0").createdAt(new Date()).createdBy("ADMIN").build())
                .variants(List.of(VariantResponseDto.builder().variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()).stock(Stock.builder().id(1L).variantId(1L).available(0L).book(0L).sold(100L).createdAt(new Date()).createdBy("ADMIN").build()).price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()).build()))
                .build();

        ItemResponse itemResponseAfterBook = ItemResponse.builder()
                .item(Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("T-Shirt dengan bahan kualitas baik").itemCategory("Pakaian Pria").itemSku("1872GNC0").createdAt(new Date()).createdBy("ADMIN").build())
                .variants(List.of(VariantResponseDto.builder().variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()).stock(Stock.builder().id(1L).variantId(1L).available(98L).book(2L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build()).price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()).build()))
                .build();

        Mockito.when(itemService.getItem(ArgumentMatchers.anyLong())).thenReturn(itemResponse).thenReturn(itemResponseAfterBook);

        Exception e = null;

        try {

            orderService.processOrder(request);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);
        Assertions.assertEquals(ResponseCodeConstants.RESPONSE_DESC_ORDER_STOCK_NOT_AVAILABLE, e.getMessage());

    }

    @Test
    void failedProcessOrderWhenStateIsPaymentAndThereIsNoBook() {

        OrderRequest request = OrderRequest.builder()
                .itemId(1L)
                .variantId(1L)
                .quantity(2L)
                .state("Payment")
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        ItemResponse itemResponse = ItemResponse.builder()
                .item(Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("T-Shirt dengan bahan kualitas baik").itemCategory("Pakaian Pria").itemSku("1872GNC0").createdAt(new Date()).createdBy("ADMIN").build())
                .variants(List.of(
                        VariantResponseDto.builder()
                                .variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build())
                                .stock(Stock.builder().id(1L).variantId(1L).available(100L).book(0L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build())
                                .price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build())
                                .build())
                )
                .build();

        ItemResponse itemResponseAfterPayment = ItemResponse.builder()
                .item(Item.builder().id(1L).itemName("T-Shirt Nike").itemDescription("T-Shirt dengan bahan kualitas baik").itemCategory("Pakaian Pria").itemSku("1872GNC0").createdAt(new Date()).createdBy("ADMIN").build())
                .variants(List.of(VariantResponseDto.builder().variant(Variant.builder().id(1L).itemId(1L).variantName("Kobe Bryant 24").variantSize("XL").variantColor("Black").variantWeight(100).createdAt(new Date()).createdBy("ADMIN").build()).stock(Stock.builder().id(1L).variantId(1L).available(98L).book(0L).sold(2L).createdAt(new Date()).createdBy("ADMIN").build()).price(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdAt(new Date()).createdBy("ADMIN").build()).build()))
                .build();

        Mockito.when(itemService.getItem(ArgumentMatchers.anyLong())).thenReturn(itemResponse).thenReturn(itemResponseAfterPayment);

        Exception e = null;

        try {

            orderService.processOrder(request);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);
        Assertions.assertEquals(ResponseCodeConstants.RESPONSE_DESC_BOOKING_NOT_FOUND, e.getMessage());

    }


}
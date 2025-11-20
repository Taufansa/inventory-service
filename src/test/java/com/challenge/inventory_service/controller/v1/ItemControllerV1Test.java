package com.challenge.inventory_service.controller.v1;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.request.ItemRequestDto;
import com.challenge.inventory_service.dto.request.PriceRequestDto;
import com.challenge.inventory_service.dto.request.StockRequestDto;
import com.challenge.inventory_service.dto.request.VariantRequestDto;
import com.challenge.inventory_service.service.impl.ItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemControllerV1Test {

    @Test
    void successCreateItem() throws Exception {

        ItemServiceImpl itemService = Mockito.mock(ItemServiceImpl.class);
        ItemControllerV1 controller = new ItemControllerV1(itemService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        ObjectMapper objectMapper = new ObjectMapper();

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

        mockMvc.perform(post("/v1/item/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andReturn();


    }

    @Test
    void failedCreateItem() throws Exception {

        ItemServiceImpl itemService = Mockito.mock(ItemServiceImpl.class);
        ItemControllerV1 controller = new ItemControllerV1(itemService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(post("/v1/item/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest())
                .andReturn();


    }
}
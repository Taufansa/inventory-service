package com.challenge.inventory_service.controller.v1;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.request.ItemRequestDto;
import com.challenge.inventory_service.dto.request.PriceRequestDto;
import com.challenge.inventory_service.dto.request.StockRequestDto;
import com.challenge.inventory_service.dto.request.UpdateItemRequest;
import com.challenge.inventory_service.dto.request.VariantRequestDto;
import com.challenge.inventory_service.dto.response.VariantResponseDto;
import com.challenge.inventory_service.model.Item;
import com.challenge.inventory_service.model.Price;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.model.Variant;
import com.challenge.inventory_service.service.impl.ItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/item/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


    }

    @Test
    void failedCreateItem() throws Exception {

        ItemServiceImpl itemService = Mockito.mock(ItemServiceImpl.class);
        ItemControllerV1 controller = new ItemControllerV1(itemService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/item/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

    }

    @Test
    void successGetItem() throws Exception {

        ItemServiceImpl itemService = Mockito.mock(ItemServiceImpl.class);
        ItemControllerV1 controller = new ItemControllerV1(itemService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/item/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    void successDestroyItem() throws Exception {

        ItemServiceImpl itemService = Mockito.mock(ItemServiceImpl.class);
        ItemControllerV1 controller = new ItemControllerV1(itemService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/item/destroy/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    void successUpdateItem() throws Exception {

        ItemServiceImpl itemService = Mockito.mock(ItemServiceImpl.class);
        ItemControllerV1 controller = new ItemControllerV1(itemService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        ObjectMapper objectMapper = new ObjectMapper();

        Item item = Item.builder().id(1L).itemName("T-Shirt Nike").itemCategory("Pakaian Pria").itemDescription("Pakaian Santai Pria").itemSku("1910MSOUUN").createdAt(new Date()).createdBy("ADMIN").build();

        UpdateItemRequest request = UpdateItemRequest.builder()
                .item(item)
                .variants(List.of(VariantResponseDto.builder()
                        .variant(Variant.builder().id(1L).variantName("T-Shirt Nike").variantWeight(100).variantColor("Black").variantSize("XL").itemId(1L).build())
                        .price(Price.builder().id(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").build())
                        .stock(Stock.builder().id(1L).variantId(1L).available(100L).book(0L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build())
                        .build()))
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/item/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


    }
}
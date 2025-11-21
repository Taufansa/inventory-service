package com.challenge.inventory_service.controller.v1;

import com.challenge.inventory_service.dto.request.OrderRequest;
import com.challenge.inventory_service.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

class OrderControllerV1Test {

    @Test
    void successOrder() throws Exception {

        OrderServiceImpl orderService = Mockito.mock(OrderServiceImpl.class);
        OrderControllerV1 controller = new OrderControllerV1(orderService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        ObjectMapper objectMapper = new ObjectMapper();

        OrderRequest request = OrderRequest.builder()
                .itemId(1L)
                .variantId(1L)
                .quantity(2L)
                .state("Payment")
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    void failedOrder() throws Exception {

        OrderServiceImpl orderService = Mockito.mock(OrderServiceImpl.class);
        OrderControllerV1 controller = new OrderControllerV1(orderService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

    }
}
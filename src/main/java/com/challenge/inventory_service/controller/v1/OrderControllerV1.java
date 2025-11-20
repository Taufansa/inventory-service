package com.challenge.inventory_service.controller.v1;

import com.challenge.inventory_service.dto.request.OrderRequest;
import com.challenge.inventory_service.dto.response.ItemResponse;
import com.challenge.inventory_service.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/order")
public class OrderControllerV1 {

    private final OrderService orderService;

    public OrderControllerV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ItemResponse order(@RequestBody OrderRequest orderRequest) {

        return orderService.processOrder(orderRequest);

    }

}

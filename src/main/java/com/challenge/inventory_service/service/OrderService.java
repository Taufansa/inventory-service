package com.challenge.inventory_service.service;

import com.challenge.inventory_service.dto.request.OrderRequest;
import com.challenge.inventory_service.dto.response.ItemResponse;

public interface OrderService {

    ItemResponse processOrder(OrderRequest orderRequest);

}

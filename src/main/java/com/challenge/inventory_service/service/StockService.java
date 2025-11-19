package com.challenge.inventory_service.service;

import com.challenge.inventory_service.dto.request.StockRequestDto;
import com.challenge.inventory_service.model.Stock;

public interface StockService {

    Stock saveStock(StockRequestDto stockRequestDto, Long variantId, String referenceNumber);

}

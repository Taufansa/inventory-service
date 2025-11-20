package com.challenge.inventory_service.service;

import com.challenge.inventory_service.dto.request.StockRequestDto;
import com.challenge.inventory_service.model.Stock;

import java.util.Optional;

public interface StockService {

    Stock saveStock(StockRequestDto stockRequestDto, Long variantId, String referenceNumber);
    Optional<Stock> getStock(Long variantId);
    void deleteStock(Long variantId);

}

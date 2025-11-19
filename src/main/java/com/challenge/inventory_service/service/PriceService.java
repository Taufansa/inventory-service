package com.challenge.inventory_service.service;

import com.challenge.inventory_service.dto.request.PriceRequestDto;
import com.challenge.inventory_service.model.Price;

public interface PriceService {

    Price savePrice(PriceRequestDto priceRequestDto, Long variantId, String referenceNumber);

}

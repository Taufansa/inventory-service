package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.CreateItemRequest;
import com.challenge.inventory_service.dto.response.VariantResponseDto;
import com.challenge.inventory_service.exception.GeneralException;
import com.challenge.inventory_service.model.Item;
import com.challenge.inventory_service.model.Price;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.model.Variant;
import com.challenge.inventory_service.repository.VariantRepository;
import com.challenge.inventory_service.service.PriceService;
import com.challenge.inventory_service.service.StockService;
import com.challenge.inventory_service.service.VariantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;
    private final PriceService priceService;
    private final StockService stockService;

    public VariantServiceImpl(VariantRepository variantRepository,  PriceService priceService, StockService stockService) {

        this.variantRepository = variantRepository;
        this.priceService = priceService;
        this.stockService = stockService;

    }

    @Transactional
    @Override
    public List<VariantResponseDto> addVariant(CreateItemRequest request, Item item) {

        List<VariantResponseDto> result = new ArrayList<>();

        try {

            log.info("Adding variants for item {} with request: {}", item, request);

            request.getVariants().forEach(
                    variant -> {
                        Variant savedVariant = variantRepository.save(Variant.builder()
                                        .itemId(item.getId())
                                        .variantName(variant.getVariantName())
                                        .variantColor(variant.getVariantColor())
                                        .variantSize(variant.getVariantSize())
                                        .variantWeight(variant.getVariantWeight())
                                .build());

                        Price savedPrice = priceService.savePrice(variant.getPrice(), savedVariant.getId(), request.getReferenceNumber());
                        Stock savedStock = stockService.saveStock(variant.getStock(), savedVariant.getId(), request.getReferenceNumber());

                        result.add(VariantResponseDto.builder()
                                        .variant(savedVariant)
                                        .price(savedPrice)
                                        .stock(savedStock)
                                .build());

                    });

        } catch (Exception e) {

            log.error("error when saving stock with error: {}", e.getMessage());

            throw new GeneralException(e.getMessage(), request.getReferenceNumber());

        }

        return result;
    }

}

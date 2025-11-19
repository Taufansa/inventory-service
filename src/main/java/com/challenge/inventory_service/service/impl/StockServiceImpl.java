package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.StockRequestDto;
import com.challenge.inventory_service.exception.GeneralException;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.repository.StockRepository;
import com.challenge.inventory_service.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {

        this.stockRepository = stockRepository;

    }

    @Transactional
    @Override
    public Stock saveStock(StockRequestDto stockRequestDto, Long variantId, String referenceNumber) {
        try {

            log.info("Saving stock for variant {} with request {} and reference {}", variantId,  stockRequestDto, referenceNumber);

            return stockRepository.saveAndFlush(Stock.builder()
                            .variantId(variantId)
                            .available(stockRequestDto.getAvailable())
                            .book(stockRequestDto.getBook())
                            .sold(stockRequestDto.getSold())
                    .build());

        } catch (Exception e) {

            log.error("error when saving stock with error: {}", e.getMessage());

            throw new GeneralException(e.getMessage(), referenceNumber);

        }
    }
}

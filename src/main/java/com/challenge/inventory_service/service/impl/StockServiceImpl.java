package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.StockRequestDto;
import com.challenge.inventory_service.exception.GeneralException;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.repository.StockRepository;
import com.challenge.inventory_service.service.StockService;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    public Optional<Stock> getStock(Long variantId) {

        try {

            log.info("Finding stock for variant id {}", variantId);

            Optional<Stock> stock = stockRepository.findByVariantId(variantId);

            log.info("Returning stock for variant id {} with result {}", variantId, stock);

            return stock;

        } catch (Exception e) {

            log.error("error when finding stock for variant {} with error {}", variantId,  e.getMessage());

            throw new GeneralException(e.getMessage());

        }

    }

    @Transactional
    @Override
    public void deleteStock(Long variantId) {

        try {

            log.info("Deleting stock for variant id {}", variantId);
            Optional<Stock> stock = stockRepository.findByVariantId(variantId);
            stock.ifPresent(stockRepository::delete);
            log.info("Deleted stock for variant id {} with result {}", variantId, stock);

        } catch (Exception e) {

            log.error("error when deleting stock for variant id {} with error {}", variantId, e.getMessage());

            throw new GeneralException(e.getMessage());

        }

    }

    @Synchronized
    @Transactional
    @Override
    public Stock updateStock(Stock stock, String referenceNumber) {
        try {

            log.info("Updating stock {} with reference {}", stock, referenceNumber);
            return stockRepository.saveAndFlush(stock);

        } catch (Exception e) {

            log.error("error when updating stock {} with error {} and reference number {}", stock, e.getMessage(), referenceNumber);
            throw new GeneralException(e.getMessage());

        }
    }
}

package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.PriceRequestDto;
import com.challenge.inventory_service.exception.GeneralException;
import com.challenge.inventory_service.model.Price;
import com.challenge.inventory_service.repository.PriceRepository;
import com.challenge.inventory_service.service.PriceService;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {

        this.priceRepository = priceRepository;

    }

    @Transactional
    @Override
    public Price savePrice(PriceRequestDto priceRequestDto, Long variantId, String referenceNumber) {

        try {

            log.info("Saving price for variant id {} with request {} and reference {}" ,variantId, priceRequestDto, referenceNumber);

            return priceRepository.saveAndFlush(Price.builder().variantId(variantId).price(priceRequestDto.getPrice()).currency(priceRequestDto.getCurrency()).build());

        } catch (Exception e) {

            log.error("error when saving price with error: {}", e.getMessage());

            throw new GeneralException(e.getMessage(), referenceNumber);

        }

    }

    @Override
    public Optional<Price> getPrice(Long variantId) {

        try {

            log.info("Getting price for variant id {}" , variantId);

            Optional<Price> prices = priceRepository.findByVariantId(variantId);

            log.info("Returning price for variant id {} with result {}" , variantId, prices);

            return prices;

        }  catch (Exception e) {

            log.error("error when getting price for variant id {} with error {}" , variantId,  e.getMessage());

            throw new GeneralException(e.getMessage());

        }

    }

    @Transactional
    @Override
    public void deletePrice(Long variantId) {
        try {

            log.info("Start deleting price for variant id {}" , variantId);
            Optional<Price> prices = priceRepository.findByVariantId(variantId);
            prices.ifPresent(priceRepository::delete);
            log.info("Finish deleting price for variant id {} with result {}" , variantId, prices);

        } catch (Exception e) {

            log.error("error when deleting price for variant id {} with error {}" , variantId, e.getMessage());
            throw new GeneralException(e.getMessage());

        }
    }

    @Synchronized
    @Transactional
    @Override
    public Price updatePrice(Price price, String referenceNumber) {

        try {

            log.info("start update price {} with reference {}" , price, referenceNumber);
            return priceRepository.saveAndFlush(price);

        } catch (Exception e) {

            log.error("error when updating price {} with error {} and reference number {}", price, e.getMessage(), referenceNumber);
            throw new GeneralException(e.getMessage());

        }

    }

}

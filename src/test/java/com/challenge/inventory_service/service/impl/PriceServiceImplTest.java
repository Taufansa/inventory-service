package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.PriceRequestDto;
import com.challenge.inventory_service.model.Price;
import com.challenge.inventory_service.repository.PriceRepository;
import com.challenge.inventory_service.service.PriceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @InjectMocks
    private PriceServiceImpl priceService;

    @Mock
    private PriceRepository priceRepository;

    @Test
    void successSavePrice() {

        PriceRequestDto priceRequestDto = PriceRequestDto.builder()
                .currency("IDR")
                .price(BigDecimal.valueOf(10000L))
                .build();
        Long variantId = 1L;
        String referenceNumber = UUID.randomUUID().toString();

        Mockito.when(priceRepository.saveAndFlush(ArgumentMatchers.any(Price.class))).thenReturn(Price.builder().variantId(1L).currency("IDR").price(BigDecimal.valueOf(10000L)).build());

        Price price = priceService.savePrice(priceRequestDto, variantId, referenceNumber);

        Assertions.assertNotNull(price);
        Assertions.assertEquals(priceRequestDto.getPrice(), price.getPrice());
        Assertions.assertEquals(priceRequestDto.getCurrency(), price.getCurrency());
        Assertions.assertEquals(variantId, price.getVariantId());

    }

    @Test
    void failedSavePrice() {

        PriceRequestDto priceRequestDto = PriceRequestDto.builder()
                .currency("IDR")
                .price(BigDecimal.valueOf(10000L))
                .build();
        Long variantId = 1L;
        String referenceNumber = UUID.randomUUID().toString();

        Mockito.when(priceRepository.saveAndFlush(ArgumentMatchers.any(Price.class))).thenThrow(new RuntimeException());

        Exception e = null;

        try {

            priceService.savePrice(priceRequestDto, variantId, referenceNumber);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);
    }
}
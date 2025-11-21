package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.PriceRequestDto;
import com.challenge.inventory_service.model.Price;
import com.challenge.inventory_service.repository.PriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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

    @Test
    void successGetPrice() {

        Mockito.when(priceRepository.findByVariantId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(150000L)).currency("IDR").createdBy("ADMIN").createdAt(new Date()).build()));
        Optional<Price> price = priceService.getPrice(1L);

        Assertions.assertTrue(price.isPresent());

    }

    @Test
    void successGetPriceButWithEmptyResult() {

        Mockito.when(priceRepository.findByVariantId(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Optional<Price> price = priceService.getPrice(1L);

        Assertions.assertFalse(price.isPresent());

    }

    @Test
    void failedGetPrice() {

        Mockito.when(priceRepository.findByVariantId(ArgumentMatchers.anyLong())).thenThrow(new RuntimeException());

        Exception e = null;

        try {

            priceService.getPrice(1L);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);

    }

    @Test
    void successUpdatePrice() {

        Price price = Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(10000L)).currency("USD").createdAt(new Date()).createdBy("SYSTEM").build();

        Mockito.when(priceRepository.saveAndFlush(ArgumentMatchers.any(Price.class))).thenReturn(price);

        Price savedPrice = priceService.updatePrice(price, UUID.randomUUID().toString());

        Assertions.assertNotNull(savedPrice);

    }

    @Test
    void failedUpdatePrice() {

        Price price = Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(10000L)).currency("USD").createdAt(new Date()).createdBy("SYSTEM").build();

        Mockito.when(priceRepository.saveAndFlush(ArgumentMatchers.any(Price.class))).thenThrow(new RuntimeException());

        Exception e = null;

        try {

            priceService.updatePrice(price, UUID.randomUUID().toString());

        }  catch (Exception ex) {
            e = ex;
        }

        Assertions.assertNotNull(e);

    }

    @Test
    void successDeletePrice() {

        Price price = Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(10000L)).currency("USD").createdAt(new Date()).createdBy("SYSTEM").build();

        Mockito.when(priceRepository.findByVariantId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(price));
        Mockito.doNothing().when(priceRepository).delete(price);

        Exception e = null;
        try {

            priceService.deletePrice(1L);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNull(e);
    }

    @Test
    void failedDeletePrice() {

        Price price = Price.builder().id(1L).variantId(1L).price(BigDecimal.valueOf(10000L)).currency("USD").createdAt(new Date()).createdBy("SYSTEM").build();

        Mockito.when(priceRepository.findByVariantId(ArgumentMatchers.anyLong())).thenThrow(new RuntimeException());

        Exception e = null;
        try {

            priceService.deletePrice(1L);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);
    }
}
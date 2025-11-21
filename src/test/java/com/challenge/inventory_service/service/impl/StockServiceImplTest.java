package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.dto.request.StockRequestDto;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @InjectMocks
    private StockServiceImpl stockServiceImpl;

    @Mock
    private StockRepository stockRepository;

    @Test
    void successSaveStock() {

        StockRequestDto stockRequestDto = StockRequestDto.builder()
                .available(100L)
                .book(0L)
                .sold(0L)
                .build();
        Long variantId = 1L;
        String referenceNumber = UUID.randomUUID().toString();

        Mockito.when(stockRepository.saveAndFlush(ArgumentMatchers.any(Stock.class))).thenReturn(Stock.builder().variantId(1L).available(100L).book(0L).sold(0L).build());

        Stock stock = stockServiceImpl.saveStock(stockRequestDto, variantId, referenceNumber);

        Assertions.assertNotNull(stock);
        Assertions.assertEquals(stockRequestDto.getAvailable(), stock.getAvailable());
        Assertions.assertEquals(stockRequestDto.getBook(), stock.getBook());
        Assertions.assertEquals(stockRequestDto.getSold(), stock.getSold());
        Assertions.assertEquals(variantId, stock.getVariantId());

    }

    @Test
    void failedSaveStock() {

        StockRequestDto stockRequestDto = StockRequestDto.builder()
                .available(100L)
                .book(0L)
                .sold(0L)
                .build();
        Long variantId = 1L;
        String referenceNumber = UUID.randomUUID().toString();

        Mockito.when(stockRepository.saveAndFlush(ArgumentMatchers.any(Stock.class))).thenThrow(RuntimeException.class);

        Exception e = null;

        try {

            stockServiceImpl.saveStock(stockRequestDto, variantId, referenceNumber);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);

    }

    @Test
    void successGetStock() {

        Mockito.when(stockRepository.findByVariantId(1L)).thenReturn(Optional.of(Stock.builder().id(1L).variantId(1L).available(100L).book(0L).sold(0L).createdAt(new Date()).createdBy("ADMIN").build()));
        Optional<Stock> stock = stockServiceImpl.getStock(1L);

        Assertions.assertTrue(stock.isPresent());

    }

    @Test
    void successGetStockButWithEmptyResult() {

        Mockito.when(stockRepository.findByVariantId(1L)).thenReturn(Optional.empty());
        Optional<Stock> stock = stockServiceImpl.getStock(1L);

        Assertions.assertFalse(stock.isPresent());

    }

    @Test
    void failedGetStock() {

        Mockito.when(stockRepository.findByVariantId(1L)).thenThrow(RuntimeException.class);

        Exception e = null;

        try {

            stockServiceImpl.getStock(1L);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);

    }

    @Test
    void successDeleteStock() {

        Stock stock = Stock.builder().id(1L).variantId(1L).available(99L).book(1L).sold(0L).createdAt(new Date()).createdBy("SYSTEM").build();
        Mockito.when(stockRepository.findByVariantId(1L)).thenReturn(Optional.of(stock));
        Mockito.doNothing().when(stockRepository).delete(stock);

        Exception e = null;

        try {

            stockServiceImpl.deleteStock(1L);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNull(e);

    }

    @Test
    void failedDeleteStock() {

        Stock stock = Stock.builder().id(1L).variantId(1L).available(99L).book(1L).sold(0L).createdAt(new Date()).createdBy("SYSTEM").build();
        Mockito.when(stockRepository.findByVariantId(1L)).thenThrow(RuntimeException.class);

        Exception e = null;

        try {

            stockServiceImpl.deleteStock(1L);

        } catch (Exception ex) {

            e = ex;

        }

        Assertions.assertNotNull(e);

    }

    @Test
    void successUpdateStock() {

        Stock stock = Stock.builder().id(1L).variantId(1L).available(99L).book(1L).sold(0L).createdAt(new Date()).createdBy("SYSTEM").build();
        Mockito.when(stockRepository.saveAndFlush(ArgumentMatchers.any(Stock.class))).thenReturn(stock);

        Stock updatedStock = stockServiceImpl.updateStock(stock, UUID.randomUUID().toString());

        Assertions.assertNotNull(updatedStock);

    }

    @Test
    void failedUpdateStock() {

        Stock stock = Stock.builder().id(1L).variantId(1L).available(99L).book(1L).sold(0L).createdAt(new Date()).createdBy("SYSTEM").build();
        Mockito.when(stockRepository.saveAndFlush(ArgumentMatchers.any(Stock.class))).thenThrow(RuntimeException.class);

        Exception e = null;
        try {
            stockServiceImpl.updateStock(stock, UUID.randomUUID().toString());
        }  catch (Exception ex) {
            e = ex;
        }

        Assertions.assertNotNull(e);

    }
}
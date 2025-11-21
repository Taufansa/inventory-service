package com.challenge.inventory_service.service.impl;

import com.challenge.inventory_service.constant.ResponseCodeConstants;
import com.challenge.inventory_service.dto.request.OrderRequest;
import com.challenge.inventory_service.dto.response.ItemResponse;
import com.challenge.inventory_service.exception.GeneralException;
import com.challenge.inventory_service.exception.OrderException;
import com.challenge.inventory_service.model.Stock;
import com.challenge.inventory_service.service.ItemService;
import com.challenge.inventory_service.service.OrderService;
import com.challenge.inventory_service.service.StockService;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final ItemService itemService;
    private final StockService stockService;

    public OrderServiceImpl(ItemService itemService, StockService stockService) {
        this.itemService = itemService;
        this.stockService = stockService;
    }


    @Synchronized
    @Transactional
    @Override
    public ItemResponse processOrder(OrderRequest orderRequest) {
        try {

            log.info("Processing Order Request {}", orderRequest);

            ItemResponse item = itemService.getItem(orderRequest.getItemId());

            if (Objects.isNull(item.getItem())) {

                log.info("Item not found");
                throw new OrderException(ResponseCodeConstants.RESPONSE_DESC_ORDER_ITEM_NOT_FOUND, orderRequest.getReferenceNumber());

            }

            log.info("Item {} has been found", item);

            item.getVariants().stream().filter(v -> v.getVariant().getId().equals(orderRequest.getVariantId())).forEach(variant -> {

                if ("BOOK".equalsIgnoreCase(orderRequest.getState())) {

                    log.info("Order State is {}", orderRequest.getState());

                    if (variant.getStock().getAvailable() < orderRequest.getQuantity()) {

                        log.info("Can't process order, insufficient stock {}", orderRequest);
                        throw new OrderException(ResponseCodeConstants.RESPONSE_DESC_ORDER_STOCK_NOT_AVAILABLE, orderRequest.getReferenceNumber());

                    }

                    Stock updatedStock = stockService.updateStock(
                            Stock.builder()
                                    .id(variant.getStock().getId())
                                    .variantId(variant.getVariant().getId())
                                    .available(variant.getStock().getAvailable() - orderRequest.getQuantity())
                                    .book(variant.getStock().getBook() + orderRequest.getQuantity())
                                    .sold(variant.getStock().getSold())
                                    .build(),
                            orderRequest.getReferenceNumber()
                    );

                    log.info("Stock Updated {} in state {}", updatedStock, orderRequest.getState());

                }

                if ("PAYMENT".equalsIgnoreCase(orderRequest.getState())) {

                    log.info("Order State is {}", orderRequest.getState());

                    if (variant.getStock().getBook().equals(0L)) {
                        throw new OrderException(ResponseCodeConstants.RESPONSE_DESC_BOOKING_NOT_FOUND, orderRequest.getReferenceNumber());
                    }

                    Stock updatedStock = stockService.updateStock(
                            Stock.builder()
                                    .id(variant.getStock().getId())
                                    .variantId(variant.getVariant().getId())
                                    .available(variant.getStock().getAvailable())
                                    .book(variant.getStock().getBook() - orderRequest.getQuantity())
                                    .sold(variant.getStock().getSold() + orderRequest.getQuantity())
                                    .build(),
                            orderRequest.getReferenceNumber()
                    );

                    log.info("Stock Updated {} in state {}", updatedStock, orderRequest.getState());

                }

            });

            return itemService.getItem(orderRequest.getItemId());

        } catch (Exception e) {

            log.error("error when process order with request {} and got error {} with reference number {}", orderRequest, e.getMessage(), orderRequest.getReferenceNumber());
            throw new GeneralException(e.getMessage(), orderRequest.getReferenceNumber());

        }
    }
}

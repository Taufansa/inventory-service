package com.challenge.inventory_service.repository;

import com.challenge.inventory_service.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    Optional<Price> findByVariantId(Long variantId);

}

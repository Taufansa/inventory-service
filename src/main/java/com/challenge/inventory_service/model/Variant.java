package com.challenge.inventory_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "variant", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Variant extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "variant_color")
    private String variantColor;

    @Column(name = "variant_size")
    private Integer variantSize;

    @Column(name = "variant_weight")
    private Integer variantWeight;

}

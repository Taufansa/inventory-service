package com.challenge.inventory_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public abstract class BaseModel {

    @Default
    @Column(name = "created_at")
    private Date createdAt = new Date(System.currentTimeMillis());

    @Default
    @Column(name = "created_by")
    private String createdBy = "SYSTEM";

}

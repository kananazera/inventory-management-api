package com.example.inventorymanagementapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency extends BaseEntity {

    @Column(nullable = false, unique = true, length = 3)
    private String code;

    @Column(unique = true)
    private Character symbol;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isDefault = Boolean.FALSE;
}

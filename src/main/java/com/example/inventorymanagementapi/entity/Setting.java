package com.example.inventorymanagementapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String key;

    private String value;

    private String description;
}

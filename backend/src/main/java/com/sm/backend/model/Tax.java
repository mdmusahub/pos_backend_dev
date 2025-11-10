package com.sm.backend.model;

import com.sm.backend.util.TaxCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tax")
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Enumerated(EnumType.STRING)
    private TaxCategory category;
    private Double productVariantPrice;
    private String gstPercent;
    private Long taxQuantity;
    private Double taxPrice;
    private Double totalPrice;

}

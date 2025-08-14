package com.sm.backend.model;

import com.sm.backend.response.TaxResponse;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class OrderItem {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@ManyToOne
    private Product product;
@ManyToOne
    private ProductVariant productVariant;
    private Long quantity;
    @OneToOne
    private Tax tax;
    private Double unitPrice;
    private Double totalPrice;

    @Override
    public String toString() {
        return "OrderItem \n" +
                " name = " + product.getProductName() + " " +productVariant.getVariantValue()+

                "\n quantity = " + quantity +
                "\n unitPrice = " + unitPrice +
                "\n totalPrice = " + totalPrice +"\n \n"
                ;
    }
}

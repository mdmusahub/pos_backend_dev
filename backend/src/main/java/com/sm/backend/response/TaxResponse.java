package com.sm.backend.response;

import com.sm.backend.model.Tax;
import lombok.Data;

@Data
public class TaxResponse {

    private Long id;
    private String category;
    private Double productVariantPrice;
    private String gstPercent;
    private Long taxQuantity;
    private Double taxPrice;
    private Double totalPrice;

    public TaxResponse(Tax tax) {
        this.id = tax.getId();
        this.category = tax.getCategory().toString();
        this.productVariantPrice = tax.getProductVariantPrice();
        this.gstPercent = tax.getGstPercent();
        this.taxQuantity = tax.getTaxQuantity();
        this.taxPrice = tax.getTaxPrice();
        this.totalPrice = tax.getTotalPrice();
    }
}

package com.sm.backend.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.Tax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxResponse {

    private Long id;
    private String category;
    private Double productVariantPrice;
    private String gstPercent;
    private Long taxQuantity;
    private Double taxPrice;
    private Double totalPrice;
    private String message;
    private List<TaxResponse> taxResponses;

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

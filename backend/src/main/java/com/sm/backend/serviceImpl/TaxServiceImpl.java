package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.*;
import com.sm.backend.repository.DiscountRepository;
import com.sm.backend.repository.OrderItemRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.repository.TaxRepository;
import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.GstDiscount;
import com.sm.backend.response.TaxResponse;
import com.sm.backend.service.TaxService;
import com.sm.backend.util.TaxCategory;
import com.sm.backend.util.WaiverMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;
    private final ProductVariantRepository variantRepository;
    private final DiscountRepository discountRepository;

    @Autowired
    public TaxServiceImpl(TaxRepository taxRepository,
                          ProductVariantRepository variantRepository,
                          DiscountRepository discountRepository) {
        this.taxRepository = taxRepository;
        this.variantRepository = variantRepository;
        this.discountRepository = discountRepository;
    }


    @Override
    public List<TaxResponse> findAll() {
        List<Tax> taxList = taxRepository.findAll();
        return taxList.stream().map(TaxResponse::new).toList();
    }

    @Override
    public TaxResponse findById(Long id) {
        return new TaxResponse(taxRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Id not FOund")));
    }

    @Override
    public void deleteById(Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Id not Found"));
        taxRepository.delete(tax);

    }

    @Override
    public ResponseEntity<GstDiscount> createTax(Long id) {
        Optional<ProductVariant> productVariant = variantRepository.findById(id);
        ProductVariant variant = productVariant.get();
//        tax.setProductVariantPrice(item.getUnitPrice());
        Double gstAmount = 0d;
        String gstPercentage = "0%";
        Double discountAmount = 0d;
        String discountPercentage = "0%";

//        tax.setGstPercent("0%");
//        tax.setTaxQuantity(0L);
//        tax.setTaxPrice(0d);
//        tax.setTotalPrice(0d);

//        Category category = item.getProductVariant().getProduct().getCategory().getParentId();
        Category category = variant.getProduct().getCategory().getParentId();

        if (category.getName().toUpperCase().equals(TaxCategory.ELECTRONICS.toString())) {
//            tax.setCategory(TaxCategory.ELECTRONICS);
//            tax.setGstPercent("18%");
//            tax.setTaxQuantity(tax.getTaxQuantity() + orderItem.getQuantity());
//            tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.18d * tax.getTaxQuantity());
//            tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
//            tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
//            gstSumPrice += tax.getTaxPrice();
            gstAmount = gstAmount+variant.getPrice() *0.18;
            gstPercentage = "18%";
        }
        if (category.getName().toUpperCase().equals(TaxCategory.SNACKS.toString())) {
//            tax.setCategory(TaxCategory.SNACKS);
//            tax.setGstPercent("12%");
//            tax.setTaxQuantity(tax.getTaxQuantity() + orderItem.getQuantity());
//            tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.12d * tax.getTaxQuantity());
//            tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
//            tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
//            gstSumPrice += tax.getTaxPrice();
            gstAmount = gstAmount+variant.getPrice() *0.12;
            gstPercentage = "12%";

        }
        if (category.getName().toUpperCase().equals(TaxCategory.DAIRY.toString())) {
//            tax.setCategory(TaxCategory.DAIRY);
//            tax.setGstPercent("5%");
//            tax.setTaxQuantity(tax.getTaxQuantity() + orderItem.getQuantity());
//            tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.05d * tax.getTaxQuantity());
//            tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
//            tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
//            gstSumPrice += tax.getTaxPrice();
            gstAmount = gstAmount+variant.getPrice() *0.05;
            gstPercentage = "5%";
        }
        if (category.getName().toUpperCase().equals(TaxCategory.GROCERY.toString())) {
//            tax.setCategory(TaxCategory.GROCERY);
//            tax.setGstPercent("5%");
//            tax.setTaxQuantity(tax.getTaxQuantity() + orderItem.getQuantity());
//            tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.05d * tax.getTaxQuantity());
//            tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
//            tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
//            gstSumPrice += tax.getTaxPrice();
            gstAmount = gstAmount+variant.getPrice() *0.05;
            gstPercentage = "5%";
        }


        Optional<Discount> discount = discountRepository.findDiscountByVariantId(variant.getId());
        if (discount.isPresent()) {
            if (discount.get().getWaiverMode() == WaiverMode.PERCENT) {
               discountAmount = variant.getPrice() * discount.get().getDiscountValue() / 100;
               discountPercentage = discount.get().getDiscountValue()+"%";
                System.out.println("Discount amount % "+discountAmount);

            }
            if (discount.get().getWaiverMode() == WaiverMode.FIXED) {
                discountAmount = discount.get().getDiscountValue();
                discountPercentage = discount.get().getDiscountValue()+"₹";
                System.out.println("Discount Amount ₹ "+discountAmount);
            }
        }

        Double totalAmount = variant.getPrice()-discountAmount+gstAmount;
        System.out.println("total amount "+totalAmount);

        GstDiscount gstDiscount = GstDiscount.builder()
                .discountAmount(discountAmount)
                .discountPercentage(discountPercentage)
                .gstAmount(gstAmount)
                .gstPercentage(gstPercentage)
                .totalAmount(totalAmount)
                .build();
        return ResponseEntity.ok(gstDiscount );
    }

    @Override
    public Object updateById(Long id, TaxRequest taxRequest) {
        Tax tax = taxRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Invalid Id"));

        if(taxRequest.getCategory() != null){
            tax.setCategory(taxRequest.getCategory());
        }
        return taxRepository.save(tax);
    }
}

package com.sm.backend.factory.tax;

import com.sm.backend.model.Category;
import com.sm.backend.model.Discount;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.model.Tax;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.repository.DiscountRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.GstDiscount;
import com.sm.backend.response.TaxResponse;
import com.sm.backend.util.RequestType;
import com.sm.backend.util.TaxCategory;
import com.sm.backend.util.WaiverMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateTax implements TaxOperation{

    private final ProductVariantRepository variantRepository;
    private final DiscountRepository discountRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.CREATE;
    }

    @Override
    public TaxResponse execute(TaxRequest request) {
        Optional<ProductVariant> productVariant = variantRepository.findById(request.getVariantId());
        ProductVariant variant = productVariant.get();
//        tax.setProductVariantPrice(item.getUnitPrice());
        Tax tax = new Tax();

        Double gstAmount = 0d;
        String gstPercentage = "0%";
        Double discountAmount = 0d;
        String discountPercentage = "0%";

        tax.setGstPercent("0%");
        tax.setTaxQuantity(0L);
        tax.setTaxPrice(0d);
        tax.setTotalPrice(0d);

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

        return TaxResponse.builder()
                .message("Tax created successfully")
                .build();
    }
}

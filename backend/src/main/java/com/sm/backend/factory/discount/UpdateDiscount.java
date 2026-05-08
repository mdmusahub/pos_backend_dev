package com.sm.backend.factory.discount;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Discount;
import com.sm.backend.repository.DiscountRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDiscount implements DiscountOperation{

    private final DiscountRepository discountRepository;
    private final ProductVariantRepository variantRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.UPDATE;
    }

    @Override
    public DiscountResponse execute(DiscountRequest request) {
        Discount discount =
                discountRepository.findById(request.getDiscountId())
                        .orElseThrow(() -> new
                                ResourceNotFoundException("invalid id"));
        if(request.getDiscountName()!=null){
            discount.setDiscountName(request.getDiscountName());
        }
        if (request.getDiscountValue()!=null){
            discount.setDiscountValue(request.getDiscountValue());
        }
        if(request.getWaiverMode()!=null){
            discount.setWaiverMode(request.getWaiverMode());
        }
        if(request.getEndDateTime()!=null){
            discount.setEndDateTime(request.getEndDateTime());
        }
        if(request.getVariantId()!=null){
            discount.setVariant(variantRepository.findById(
                    request.getVariantId())
                    .orElseThrow(()->new ResourceNotFoundException(
                            "invalid id")));
        }

        discountRepository.save(discount);

        return DiscountResponse.builder()
                .message("Updated discount with id " + request.getDiscountId())
                .build();
    }
}

package com.sm.backend.factory.discount;

import com.sm.backend.exceptionalHandling.DiscountAlreadyExistException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Discount;
import com.sm.backend.repository.DiscountRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;
import com.sm.backend.util.RequestType;
import com.sm.backend.util.WaiverMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateDiscount implements DiscountOperation {

    private final DiscountRepository discountRepository;
    private final ProductVariantRepository variantRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.CREATE;
    }

    @Override
    public DiscountResponse execute(DiscountRequest request) {

        Optional<Discount> discountCheck = discountRepository.findDiscountByVariantId(request.getVariantId());
        if (discountCheck.isPresent()) {
            throw new DiscountAlreadyExistException("discount already exist");
        } else {
            Discount discount = new Discount();
            discount.setDiscountName(request.getDiscountName());
//        discount.setVariant(variantRepository);
            if (request.getWaiverMode() == WaiverMode.FIXED) {
                discount.setWaiverMode(WaiverMode.FIXED);

            }
            if (request.getWaiverMode() == WaiverMode.PERCENT) {
                discount.setWaiverMode(WaiverMode.PERCENT);
            }
            discount.setVariant(variantRepository.findById(
                    request.getVariantId()).orElseThrow(() ->
                    new ResourceNotFoundException("invalid id")));
            discount.setDiscountValue(request.getDiscountValue());
            discount.setEndDateTime(request.getEndDateTime());
            discount.setIsActive(true);
            discountRepository.save(discount);
            return DiscountResponse.builder()
                    .message("Discount created successfully with ID " +
                            discount.getId())
                    .build();
        }
    }
}

package com.sm.backend.factory.discount;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Discount;
import com.sm.backend.repository.DiscountRepository;
import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadDiscountById implements DiscountOperation{

    private final DiscountRepository discountRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_ID;
    }

    @Override
    public DiscountResponse execute(DiscountRequest request) {

        Discount discount =
                discountRepository.findById(request.getDiscountId())
                        .orElseThrow(()->
                                new ResourceNotFoundException(
                                "invalid id"));
        return new DiscountResponse(discount);
    }
}

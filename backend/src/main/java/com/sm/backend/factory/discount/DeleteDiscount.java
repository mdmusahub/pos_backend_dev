package com.sm.backend.factory.discount;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.repository.DiscountRepository;
import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDiscount implements DiscountOperation{

    private final DiscountRepository discountRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.DELETE;
    }

    @Override
    public DiscountResponse execute(DiscountRequest request) {
        discountRepository.delete(discountRepository.findById(
                request.getDiscountId()).orElseThrow(()->
                new ResourceNotFoundException("invalid id")));

        return DiscountResponse.builder()
                .message("Deleted discount with id " + request.getDiscountId())
                .build();
    }
}

package com.sm.backend.factory.discount;

import com.sm.backend.model.Discount;
import com.sm.backend.repository.DiscountRepository;
import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllDiscounts implements DiscountOperation {

    private final DiscountRepository discountRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.READ_ALL;
    }

    @Override
    public DiscountResponse execute(DiscountRequest request) {

        List<Discount> discounts = discountRepository.findAll();
        return DiscountResponse.builder()
                .discounts(discounts.stream().map(
                        DiscountResponse::new).toList())
                .build();
    }
}

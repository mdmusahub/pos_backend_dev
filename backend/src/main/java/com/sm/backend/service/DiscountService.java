package com.sm.backend.service;

import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;

import java.util.List;

public interface DiscountService {
    void createDiscount(DiscountRequest request);

    List<DiscountResponse> getAll();

    DiscountResponse getById(Long id);

    void delete(Long id);

    void update(DiscountRequest request, Long id);
}

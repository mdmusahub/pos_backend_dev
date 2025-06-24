package com.sm.backend.service;

import com.sm.backend.request.CustomerRequest;
import com.sm.backend.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse getById(Long id);

    List<CustomerResponse> getAll();

    void update(CustomerRequest request, Long id);

    void delete(Long id);
}

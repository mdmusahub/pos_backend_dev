package com.sm.backend.service;

import com.sm.backend.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse getById(Long id);
}

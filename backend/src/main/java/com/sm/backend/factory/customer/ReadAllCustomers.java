package com.sm.backend.factory.customer;

import com.sm.backend.model.Customer;
import com.sm.backend.repository.CustomerRepository;
import com.sm.backend.request.CustomerRequest;
import com.sm.backend.response.CustomerResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllCustomers implements CustomerOperation{

    private final CustomerRepository customerRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_ALL;
    }

    @Override
    public CustomerResponse execute(CustomerRequest request) {

        List<Customer> customers = customerRepository.findAll();
        return CustomerResponse.builder()
                .customers(customers.stream().map(CustomerResponse::new).toList())
                .build();
    }
}

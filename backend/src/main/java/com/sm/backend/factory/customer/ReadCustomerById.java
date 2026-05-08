package com.sm.backend.factory.customer;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Customer;
import com.sm.backend.repository.CustomerRepository;
import com.sm.backend.request.CustomerRequest;
import com.sm.backend.response.CustomerResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadCustomerById implements CustomerOperation{

    private final CustomerRepository customerRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_ID;
    }

    @Override
    public CustomerResponse execute(CustomerRequest request) {
        Customer customer =
                customerRepository.findById(request.getCustomerId()).orElseThrow(() ->
                new ResourceNotFoundException("invalid customerId"));
        return new CustomerResponse(customer);
    }
}

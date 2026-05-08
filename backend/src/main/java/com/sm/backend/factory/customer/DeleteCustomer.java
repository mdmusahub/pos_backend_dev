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
public class DeleteCustomer implements CustomerOperation{

    private final CustomerRepository customerRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.DELETE;
    }

    @Override
    public CustomerResponse execute(CustomerRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        try {
            customerRepository.delete(customer);
        } catch (Exception e) {
            throw new ResourceNotFoundException(
                    "This customer is involved " +
                    "in an order.");
        }
        return CustomerResponse.builder()
                .message("Customer with ID " + request.getCustomerId() +
                        " has been deleted successfully.")
                .build();
    }
}

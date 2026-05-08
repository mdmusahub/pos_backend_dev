package com.sm.backend.factory.customer;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Customer;
import com.sm.backend.model.Order;
import com.sm.backend.repository.CustomerRepository;
import com.sm.backend.repository.OrderRepository;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.request.CustomerRequest;
import com.sm.backend.response.CustomerResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateCustomers implements CustomerOperation{

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.UPDATE;
    }

    @Override
    public CustomerResponse execute(CustomerRequest request) {
        Customer customer =
                customerRepository.findById(
                        request.getCustomerId()).orElseThrow(() ->
                new ResourceNotFoundException("invalid id"));
        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }
        List<Order> orders = orderRepository.findAllOrdersByCustomer(customer);
        if(orders.isEmpty() == false){
            for(Order order : orders){
                order.setUserPhoneNumber(request.getPhoneNumber());
                orderRepository.save(order);
            }
        }
        customerRepository.save(customer);
        return CustomerResponse.builder()
                .message("Updated")
                .build();
    }
}

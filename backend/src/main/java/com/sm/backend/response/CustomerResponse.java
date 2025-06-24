package com.sm.backend.response;



import com.sm.backend.model.Customer;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CustomerResponse {
    private Long customerId;
    private String phoneNumber;
    private LocalDateTime createdAt;
    public CustomerResponse(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.phoneNumber = customer.getPhoneNumber();
        this.createdAt = customer.getCreatedAt();
    }


}

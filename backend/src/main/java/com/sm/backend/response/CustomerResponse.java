package com.sm.backend.response;



import com.sm.backend.model.Customer;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CustomerResponse {
    private Long customerId;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private String email;
    public CustomerResponse(Customer customer) {
        this.customerId = customer.getId();
        this.phoneNumber = customer.getPhoneNumber();
        this.createdAt = customer.getCreatedAt();
        this.email = customer.getEmail();
    }


}

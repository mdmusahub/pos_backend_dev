package com.sm.backend.response;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class CustomerResponse {
    private Long customerId;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private String email;
    private List<CustomerResponse> customers;
    private String message;

    public CustomerResponse(Customer customer) {
        this.customerId = customer.getId();
        this.phoneNumber = customer.getPhoneNumber();
        this.createdAt = customer.getCreatedAt();
        this.email = customer.getEmail();
    }


}

package com.sm.backend.request;

import com.sm.backend.util.RequestType;
import lombok.Data;

@Data
public class CustomerRequest {
private String phoneNumber;
private String email;
private RequestType requestType;
private Long customerId;
}

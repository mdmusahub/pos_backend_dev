package com.sm.backend.factory.customer;

import com.sm.backend.util.RequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomerFactory {

    private final List<CustomerOperation> operations;

    private final Map<RequestType, CustomerOperation> operationMap;

    @PostConstruct
    public void initializeMap(){
        operations.forEach(
                op -> {
                    operationMap.put(op.getRequestType(), op);
                }
        );
    }

    public CustomerOperation getOperation(RequestType requestType){
        return operationMap.get(requestType);
    }

}

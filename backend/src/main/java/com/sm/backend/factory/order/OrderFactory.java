package com.sm.backend.factory.order;

import com.sm.backend.util.RequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderFactory {

    private final List<OrderOperation> operations;

    private final Map<RequestType, OrderOperation> operationMap =
            new HashMap<>();

    @PostConstruct
    public void init() {
        operations.forEach(op -> {
            operationMap.put(op.getRequestType(), op);
        });
    }

    public OrderOperation getOperation(RequestType requestType) {
        return operationMap.get(requestType);
    }
}

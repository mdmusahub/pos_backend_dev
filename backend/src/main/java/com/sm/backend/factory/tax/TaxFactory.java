package com.sm.backend.factory.tax;

import com.sm.backend.util.RequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TaxFactory {

    private final List<TaxOperation> operations;
    private final Map<RequestType, TaxOperation> operationMap = new HashMap<>();

    @PostConstruct
    public void init() {
        operations.forEach(op -> {
            operationMap.put(op.getRequestType(), op);
        });
    }

    public TaxOperation getOperation(RequestType requestType) {
        return operationMap.get(requestType);
    }
}

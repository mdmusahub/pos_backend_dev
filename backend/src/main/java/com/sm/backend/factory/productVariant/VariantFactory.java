package com.sm.backend.factory.productVariant;

import com.sm.backend.util.RequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class VariantFactory {

    private final List<VariantOperation> operations;

    private final Map<RequestType, VariantOperation> operationMap = new HashMap<>();

    @PostConstruct
    public void init() {
        operations.forEach(op -> {
            operationMap.put(op.getRequestType(), op);
        });
    }

    public VariantOperation getOperation(RequestType requestType) {
        return operationMap.get(requestType);
    }

}

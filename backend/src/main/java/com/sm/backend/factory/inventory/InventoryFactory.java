package com.sm.backend.factory.inventory;

import com.sm.backend.util.RequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class    InventoryFactory {

    private final List<InventoryOperation> operations;

    private final Map<RequestType, InventoryOperation> operationMap =
            new HashMap<>();

    @PostConstruct
    public void initializeOperationMap() {
        operations.forEach(op ->{
            operationMap.put(op.getRequestType(), op);
        });
    }

    public InventoryOperation getOperation(RequestType requestType) {
        return operationMap.get(requestType);
    }
}

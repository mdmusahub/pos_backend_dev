package com.sm.backend.factory.discount;

import com.sm.backend.util.DiscountType;
import com.sm.backend.util.RequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DiscountFactory {

    private final List<DiscountOperation> operations;
    private final Map<RequestType, DiscountOperation> operationMap;

    @PostConstruct
    public void initializeMap(){
        operations.forEach(
                op -> {
                    operationMap.put(op.getRequestType(), op);
                }
        );
    }

    public DiscountOperation getOperation(RequestType requestType){
        return operationMap.get(requestType);
    }
}

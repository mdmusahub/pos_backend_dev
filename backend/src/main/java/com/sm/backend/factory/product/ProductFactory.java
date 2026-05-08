package com.sm.backend.factory.product;

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
public class ProductFactory {

    private final List<ProductOperation> operations;

    private final Map<RequestType,
            ProductOperation> operationMap = new HashMap<>();

    @PostConstruct
    private void initializeOperationMap(){
        operations.forEach(
                op ->
                    operationMap.put(op.getRequestType(), op)
        );
    }

    public ProductOperation getOperation(RequestType request){
        return operationMap.get(request);
    }
}

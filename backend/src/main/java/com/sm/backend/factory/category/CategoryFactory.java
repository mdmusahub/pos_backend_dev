package com.sm.backend.factory.category;

import com.sm.backend.util.RequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CategoryFactory {

    private final List<CategoryOperation> operations;

    private final Map<RequestType, CategoryOperation> operationMap = new HashMap<>();

    @PostConstruct
    public void initializeMap(){
        operations.forEach(
                op ->{
                    operationMap.put(op.getRequestType(), op);
                });
    }

    public CategoryOperation getOperation(RequestType requestType){
        return operationMap.get(requestType);
    }
}

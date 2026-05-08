package com.sm.backend.factory.returnOrder;

import com.sm.backend.util.RequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReturnFactory {

    private final List<ReturnOperation> operations;

    private final Map<RequestType, ReturnOperation> operationMap;

    @PostConstruct
    public void initializeMap(){
        operations.forEach(
                op -> {
                    operationMap.put(op.getRequestType(), op);
                }
        );
    }

    public ReturnOperation getOperation(RequestType requestType){
        return operationMap.get(requestType);
    }
}

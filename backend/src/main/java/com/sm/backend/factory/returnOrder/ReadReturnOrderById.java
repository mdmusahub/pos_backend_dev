package com.sm.backend.factory.returnOrder;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.ReturnOrder;
import com.sm.backend.model.ReturnOrderItem;
import com.sm.backend.repository.ReturnOrderItemRepository;
import com.sm.backend.repository.ReturnOrderRepository;
import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.response.ReturnOrderResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadReturnOrderById implements ReturnOperation {

    private final ReturnOrderRepository returnOrderRepository;
    private final ReturnOrderItemRepository returnOrderItemRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_ID;
    }

    @Override
    public ReturnOrderResponse execute(ReturnOrderRequest request) {

        ReturnOrder returnOrder =
                returnOrderRepository.findById(
                        request.getReturnId())
                        .orElseThrow((

                        )->new ResourceNotFoundException(
                                "ReturnOrder id not found"));
        List<ReturnOrderItem> returnOrderItems = returnOrderItemRepository
                .findAllReturnOrderItemsByOrder(returnOrder.getOrder());
        return new ReturnOrderResponse(returnOrder,returnOrderItems);
    }
}

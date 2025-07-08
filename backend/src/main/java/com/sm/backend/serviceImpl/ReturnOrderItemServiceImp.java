package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.*;
import com.sm.backend.repository.*;
import com.sm.backend.request.OrderItemRequest;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.request.ReturnQuantityRequest;
//import com.sm.backend.response.ReturnOrderItemResponse;
import com.sm.backend.service.ReturnOrderItemService;
import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnOrderItemServiceImp implements ReturnOrderItemService {

    private final ReturnOrderItemRepository returnOrderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final OrderItemRepository orderItemRepository;
    @Autowired
    public ReturnOrderItemServiceImp(ReturnOrderItemRepository returnOrderItemRepository,
                                     OrderRepository orderRepository,
                                     ProductRepository productRepository,
                                     ProductVariantRepository productVariantRepository,
                                     OrderItemRepository orderItemRepository) {
        this.returnOrderItemRepository = returnOrderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.orderItemRepository = orderItemRepository;
    }


    @Override
    public void CreateReturnOrder(ReturnOrderRequest request) throws ResourceNotFoundException{



    ReturnOrder returnOrder = new ReturnOrder();


    returnOrder.setReturnQuantity(0L);
    returnOrder.setRefundAmount(0d);
      Order order = orderRepository.findById(request.getOrderId()).orElseThrow(()->
              new ResourceNotFoundException("Invalid Id "));
       List<ReturnQuantityRequest> returnQuantityRequest = request.getReturnQuantityRequestList();
        List<ReturnOrderItem> items =returnQuantityRequest.stream().map(m ->{

            ReturnOrderItem returnOrderItem = new ReturnOrderItem();
            returnOrderItem.setReturnQuantity(m.getReturnQuantity());
            returnOrder.setReturnQuantity(returnOrder.getReturnQuantity()+ m.getReturnQuantity());
            OrderItem orderItem = orderItemRepository.findById(m.getOrderItemId()).orElseThrow(()->
                    new ResourceNotFoundException("Order Item Not Found"));
            returnOrderItem.setOrderItem(orderItem);
            returnOrderItem.setProductId(orderItem.getProduct());
            returnOrderItem.setProductVariantId(orderItem.getProductVariant());
            returnOrderItem.setUnitPrice(orderItem.getUnitPrice());
            returnOrderItem.setRefundAmount(orderItem.getUnitPrice() * m.getReturnQuantity());
            returnOrder.setRefundAmount(returnOrder.getRefundAmount() + m.getReturnQuantity());
            returnOrderItem.setOrderId(order);
            returnOrderItem.setRequestedBy(order.getCustomer());
            return returnOrderItem;
        }).toList();
        returnOrderItemRepository.saveAll(items);

    }

//    @Override
//    public List<ReturnOrderItemResponse> findAll() {
//        return List.of();
//    }
//
//    @Override
//    public ReturnOrderItemResponse findById(Long id) {
//        return null;
//    }
}

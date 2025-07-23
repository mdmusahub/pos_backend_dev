package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.*;
import com.sm.backend.repository.*;
import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.request.ReturnQuantityRequest;
import com.sm.backend.response.ReturnOrderResponse;
import com.sm.backend.service.ReturnService;
import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnServiceImpl implements ReturnService {

    private final ReturnOrderItemRepository returnOrderItemRepository;
    private final ReturnOrderRepository returnOrderRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final ProductVariantRepository productVariantRepository;

    @Autowired
    public ReturnServiceImpl(ReturnOrderItemRepository returnOrderItemRepository,
                             ReturnOrderRepository returnOrderRepository,
                             OrderRepository orderRepository1,
                             OrderItemRepository orderItemRepository,
                             ProductInventoryRepository productInventoryRepository,
                             ProductVariantRepository productVariantRepository) {
        this.returnOrderItemRepository = returnOrderItemRepository;
        this.returnOrderRepository = returnOrderRepository;
        this.orderRepository = orderRepository1;
        this.orderItemRepository = orderItemRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.productVariantRepository = productVariantRepository;
    }


    @Override
    public void createReturnOrder(ReturnOrderRequest request) throws ResourceNotFoundException {

        ReturnOrder returnOrder = new ReturnOrder();
        returnOrder.setReturnQuantity(0L);
        returnOrder.setRefundAmount(0d);



        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() ->
                new ResourceNotFoundException("Order Id Not Found "));
        List<ReturnQuantityRequest> QuantityRequest = request.getReturnQuantityRequestList();
        List<ReturnOrderItem> items = new ArrayList<>();
        for (ReturnQuantityRequest returnQuantityRequest : QuantityRequest) {
            OrderItem orderItem = orderItemRepository.findById(returnQuantityRequest.getOrderItemId()).orElseThrow(() ->
                    new ResourceNotFoundException("OrderItem Id Not Found "));
            if (orderItem.getProductVariant().getRefundable()) {
                ReturnOrderItem returnOrderItem = new ReturnOrderItem();
                returnOrderItem.setReturnQuantity(returnQuantityRequest.getReturnQuantity());
                returnOrder.setReturnQuantity(returnOrder.getReturnQuantity() + returnQuantityRequest.getReturnQuantity());
                returnOrderItem.setOrderItem(orderItem);
                returnOrderItem.setProduct(orderItem.getProduct());
                returnOrderItem.setProductVariant(orderItem.getProductVariant());
                returnOrderItem.setUnitPrice(orderItem.getUnitPrice());
                returnOrderItem.setReturnQuantity(returnQuantityRequest.getReturnQuantity());
                returnOrderItem.setReturnReason(returnQuantityRequest.getReturnReason());
                returnOrderItem.setReturnStatus(returnQuantityRequest.getReturnStatus());


                if (returnQuantityRequest.getReturnStatus() == ReturnStatus.REFUNDED) {
                    returnOrderItem.setRefundAmount(orderItem.getUnitPrice() * returnQuantityRequest.getReturnQuantity());
                    returnOrder.setRefundAmount(returnOrder.getRefundAmount() + returnOrderItem.getRefundAmount());

                    if (returnQuantityRequest.getReturnReason() != ReturnReason.DAMAGE) {

                        ProductInventory inventory = productInventoryRepository.findProductInventoryByProductVariant(orderItem.getProductVariant());
                        inventory.setQuantity(inventory.getQuantity() + returnOrderItem.getReturnQuantity());
                        productInventoryRepository.save(inventory);

                    }
                } else if (returnQuantityRequest.getReturnStatus() == ReturnStatus.REPLACE) {
                    ProductInventory inventory = productInventoryRepository.findProductInventoryByProductVariant(returnOrderItem.getProductVariant());
                    inventory.setQuantity(inventory.getQuantity() - returnOrderItem.getReturnQuantity());
                    returnOrderItem.setRefundAmount(0d);
                    productInventoryRepository.save(inventory);

                }
                returnOrderItem.setOrder(order);

                items.add(returnOrderItem);
            }



            }
       returnOrder.setOrder(order);

        returnOrder.setRequestBy(order.getCustomer());

            returnOrderItemRepository.saveAll(items);
            if(!items.isEmpty()){
                returnOrderRepository.save(returnOrder);
            }
    }



    
    @Override
    public ReturnOrderResponse getById(Long id) {
        ReturnOrder returnOrder = returnOrderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("ReturnOrder id not found"));
        List<ReturnOrderItem> returnOrderItems = returnOrderItemRepository.findAllReturnOrderItemsByOrder(returnOrder.getOrder());
        return new ReturnOrderResponse(returnOrder,returnOrderItems);
    }




    @Override
    public List<ReturnOrderResponse> findAll() {

        List<ReturnOrder> returnOrders = returnOrderRepository.findAll();
        return returnOrders.stream().map(returnOrder -> {
            List<ReturnOrderItem> returnOrderItems = returnOrderItemRepository.findAllReturnOrderItemsByOrder(returnOrder.getOrder());
            return new ReturnOrderResponse(returnOrder,returnOrderItems);
        }).toList();
    }


}

package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Order;
import com.sm.backend.model.OrderItem;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.OrderItemRepository;
import com.sm.backend.repository.OrderRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.OrderItemRequest;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, ProductVariantRepository productVariantRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
    }

    @Override
    public void register(OrderRequest request) {
        Order order = new Order();
        order.setUserPhoneNumber(request.getUserPhoneNumber());
        order.setStatus(request.getStatus());
        order.setDiscount(request.getDiscount());
        order.setPaymentMode(request.getPaymentMode());
        order.setCashAmount(request.getCashAmount());
        order.setTotalAmount(request.getTotalAmount());
        order.setOnlineAmount(request.getOnlineAmount());
        order.setOrderDate(request.getOrderDate());
        order.setUpdatedAt(request.getUpdatedAt());

   List<OrderItemRequest> orderItemRequests= request.getOrderItemRequests();
        List<OrderItem> list = orderItemRequests.stream().map((x) -> {
            OrderItem item = new OrderItem();
            ProductVariant variant = productVariantRepository.findById
                            (x.getVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("variant id not found."));
            item.setProductVariant(variant);
            item.setProduct(variant.getProduct());
            item.setUnitPrice(variant.getPrice());
            item.setQuantity(x.getQuantity());
            item.setTotalPrice(variant.getPrice() * x.getQuantity());
             orderItemRepository.save(item);
             return item;
        }).toList();
        order.setOrderItems(list);
        orderRepository.save(order);
    }

    @Override
    public Object getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> list=orders.stream().map(OrderResponse::new).toList();
        return list;
    }
//
    @Override
    public Object findById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("id not found"));
     return new OrderResponse(order);
    }



    @Override
    public void deleteById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("order not found."));
        orderRepository.delete(order);
        List<OrderItem> orderItems = order.getOrderItems();
        for(OrderItem item : orderItems){
            orderItemRepository.delete(orderItemRepository.findById
            (item.getOrderItemId()).orElseThrow(()->new ResourceNotFoundException("invalid id")));
        }
//        List<Long> list = orderItems.stream().map((x) -> x.getOrderItemId()).toList();
//        List<OrderItem> deletedItems = list.stream().map((x) -> orderItemRepository.findById(x)
//                .orElseThrow(() -> new ResourceNotFoundException("invalid Order Item id"))).toList();

    }

}




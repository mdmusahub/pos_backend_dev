package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Order;
import com.sm.backend.model.OrderItem;
import com.sm.backend.repository.OrderItemRepository;
import com.sm.backend.repository.OrderRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        orderRepository.save(order);
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orderRepository.findById(order.getOrderID())
                .orElseThrow(() -> new ResourceNotFoundException("id not Found")));
        orderItem.setProductVariant(productVariantRepository.findById(request.getProductVariantId()).orElseThrow(() -> new ResourceNotFoundException("id not found")));
        orderItem.setProduct(productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("id not found")));
        orderItem.setQuantity(request.getQuantity());
        orderItem.setUnitPrice(request.getUnitPrice());
        order.setTotalAmount(request.getUnitPrice() * request.getQuantity());
        orderItemRepository.save(orderItem);
    }

    @Override
    public Object getAllOrders() {
        List<OrderResponse> list = orderRepository.findAll().stream().map(x -> {
            List<OrderItem> items = orderItemRepository.getItemsByOrderId(x.getOrderID());
            return new OrderResponse(x, items);
        }).toList();
    return list;
    }

    @Override
    public Object findById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("id not found"));
        List<OrderItem>orderItems = orderItemRepository.getItemsByOrderId(orderId);
  return new OrderResponse(order,orderItems);
    }

    @Override
    public void updateById(Long orderId, OrderRequest request) {
        OrderItem orderItem = orderItemRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("id not found"));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("id not found"));
        if (request.getUserPhoneNumber()!=null){
            order.setUserPhoneNumber(request.getUserPhoneNumber());
        }
        if (request.getStatus()!=null){
            order.setStatus(request.getStatus());
        }
        if (request.getDiscount()!=null){
            order.setDiscount(request.getDiscount());
        }

        if (request.getTax()!=null){
            order.setTax(request.getTax());
        }
        if (request.getTotalAmount()!=null){
            order.setTotalAmount(request.getTotalAmount());
        }
        if (request.getPaymentMode()!=null){
            order.setPaymentMode(request.getPaymentMode());
        }
        if (request.getOnlineAmount()!=null){
            order.setOnlineAmount(request.getOnlineAmount());
        }

        if (request.getOrderDate()!=null){
            order.setOrderDate(request.getOrderDate());
        }
        if (request.getUpdatedAt()!=null){
            order.setUpdatedAt(request.getUpdatedAt());
        }
        if (request.getQuantity()!=null){
            orderItem.setQuantity(request.getQuantity());
        }
        if(request.getUnitPrice()!=null){
            orderItem.setUnitPrice(request.getUnitPrice());
        }
        if (request.getCashAmount()!=null){
            order.setCashAmount(request.getCashAmount());
        }
        Order save = orderRepository.save(order);
        OrderItem save1 = orderItemRepository.save(orderItem);


    }

    @Override
    public void deleteById(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}




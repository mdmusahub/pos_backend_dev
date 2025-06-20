package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Order;
import com.sm.backend.model.OrderItem;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.*;
import com.sm.backend.request.OrderItemRequest;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.service.OrderService;
import com.sm.backend.utility.OrderStatus;
import com.sm.backend.utility.PaymentMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductInventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    @Autowired
    public OrderServiceImpl(ProductInventoryRepository inventoryRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, ProductVariantRepository productVariantRepository) {
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
    }

    @Override
    public void createOrder(OrderRequest request) throws ResourceNotFoundException{
        //here we're getting and setting the values of order table.
        Order order = new Order();
        order.setUserPhoneNumber(request.getUserPhoneNumber());
        if(request.getStatus()== OrderStatus.PENDING){
            order.setStatus(OrderStatus.PENDING);
        }
        if(request.getStatus()== OrderStatus.COMPLETED){
            order.setStatus(OrderStatus.COMPLETED);
        }
//        order.setDiscount(request.getDiscount());
        if(request.getPaymentMode()==PaymentMode.CASH){
            order.setPaymentMode(PaymentMode.CASH);
        }
        if(request.getPaymentMode()==PaymentMode.UPI){
            order.setPaymentMode(PaymentMode.UPI);
        }
        if(request.getPaymentMode()==PaymentMode.BOTH){
            order.setPaymentMode(PaymentMode.BOTH);
        }
//        order.setPaymentMode(request.getPaymentMode());
        order.setCashAmount(request.getCashAmount());
        order.setOnlineAmount(request.getOnlineAmount());
        order.setTotalAmount(0d);
        order.setOrderDate(request.getOrderDate());
        order.setUpdatedAt(request.getUpdatedAt());

//here we are creating and setting the orderItems in the order.
   List<OrderItemRequest> orderItemRequests= request.getOrderItemRequests();
        List<OrderItem> list = orderItemRequests.stream().map(x -> {

            OrderItem item = new OrderItem();

            ProductVariant variant = productVariantRepository.findById
                            (x.getVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("invalid variant ID."));

            item.setProductVariant(variant);
            item.setProduct(variant.getProduct());
            item.setUnitPrice(variant.getPrice());
            //if order quantity is greater than inventory quantity then this will throw an exception.
            if(inventoryRepository.findProductInventoryByProductVariant(variant).getQuantity()>=x.getQuantity())
            {item.setQuantity(x.getQuantity());}
            else {
                throw new  ResourceNotFoundException("out of stock.");
            }
            item.setTotalPrice(variant.getPrice() * x.getQuantity());
            order.setTotalAmount(order.getTotalAmount()+ item.getTotalPrice());
             orderItemRepository.save(item);
             return item;
        }).toList();
        order.setOrderItems(list);
//        setting tax
        order.setTax(order.getTotalAmount()*0.18d);
//        applying tax to the total amount
        order.setTotalAmount(order.getTotalAmount()+ order.getTax());
//        setting 5% discount if total amount is >= 5000
        if (order.getTotalAmount()>=5000d){
            order.setDiscount(order.getTotalAmount()*0.05d);
            order.setTotalAmount(order.getTotalAmount() - order.getDiscount());
        }
        else {
            order.setDiscount(0d);
        }
        orderRepository.save(order);
//        managing inventory
        for(OrderItem item:list){
            ProductVariant variant = item.getProductVariant();
            ProductInventory inventory = inventoryRepository.findProductInventoryByProductVariant(variant);
            inventory.setQuantity(inventory.getQuantity()-item.getQuantity());
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public List<OrderResponse> getAll(Integer pageNumber,Integer pageSize, String sortby,String sortDir) {
        Sort sort = null;

        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortby).ascending();
        } else {
            sort = Sort.by(sortby).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> all = orderRepository.findAll(pageable);
        return all.stream().map(OrderResponse::new).toList();
    }
//

    @Override
    public Object getById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("invalid order ID"));
     return new OrderResponse(order);
    }


    @Override
    public void delete(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("invalid order ID"));
        List<OrderItem> orderItems = order.getOrderItems();
        orderRepository.delete(order);
        for(OrderItem item : orderItems){
            orderItemRepository.delete(orderItemRepository.findById
            (item.getOrderItemId()).orElseThrow(()->new ResourceNotFoundException("invalid order item ID")));
        }
//        List<Long> list = orderItems.stream().map((x) -> x.getOrderItemId()).toList();
//        List<OrderItem> deletedItems = list.stream().map((x) -> orderItemRepository.findById(x)
//                .orElseThrow(() -> new ResourceNotFoundException("invalid Order Item id"))).toList();

    }

}




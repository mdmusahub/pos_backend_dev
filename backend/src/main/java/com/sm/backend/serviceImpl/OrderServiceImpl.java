package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.DiscountAlreadyExistException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.*;
import com.sm.backend.repository.*;
import com.sm.backend.request.OrderItemRequest;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.service.OrderService;
import com.sm.backend.util.WaiverMode;
import com.sm.backend.utility.OrderStatus;
import com.sm.backend.utility.PaymentMode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductInventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CustomerRepository customerRepository;
    private final DiscountRepository discountRepository;

    @Autowired
    public OrderServiceImpl(ProductInventoryRepository inventoryRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductVariantRepository productVariantRepository, CustomerRepository customerRepository, DiscountRepository discountRepository) {
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productVariantRepository = productVariantRepository;
        this.customerRepository = customerRepository;
        this.discountRepository = discountRepository;
    }

    @Override
    public void createOrder(OrderRequest request) throws ResourceNotFoundException {
        //here we're getting and setting the values of order table.
        Order order = new Order();
        order.setUserPhoneNumber(request.getUserPhoneNumber());
        Optional<Customer> byPhoneNumber = customerRepository.findByPhoneNumber(request.getUserPhoneNumber());
        if (byPhoneNumber.isPresent()) {
            order.setCustomer(byPhoneNumber.get());
        } else {
            Customer customer = new Customer();
            customer.setPhoneNumber(request.getUserPhoneNumber());
            customer.setEmail(request.getEmail());
            customerRepository.save(customer);
            order.setCustomer(customer);
        }

        if (request.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.PENDING);
        }
        if (request.getStatus() == OrderStatus.COMPLETED) {
            order.setStatus(OrderStatus.COMPLETED);
        }
        if (request.getPaymentMode() == PaymentMode.CASH) {
            order.setPaymentMode(PaymentMode.CASH);
        }
        if (request.getPaymentMode() == PaymentMode.UPI) {
            order.setPaymentMode(PaymentMode.UPI);
        }
        if (request.getPaymentMode() == PaymentMode.BOTH) {
            order.setPaymentMode(PaymentMode.BOTH);
        }

        order.setCashAmount(request.getCashAmount());
        order.setOnlineAmount(request.getOnlineAmount());
        order.setDiscount(0d);
        order.setTotalAmount(0d);

//here we are creating and setting the orderItems in the order.
        List<OrderItemRequest> orderItemRequests = request.getOrderItemRequests();
        List<OrderItem> list = orderItemRequests.stream().map(x -> {

            OrderItem item = new OrderItem();

            ProductVariant variant = productVariantRepository.findById
                            (x.getVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("invalid variant ID."));

            item.setProductVariant(variant);
            item.setProduct(variant.getProduct());
            item.setUnitPrice(variant.getPrice());
            //if order quantity is greater than inventory quantity then this will throw an exception.
            if (inventoryRepository.findProductInventoryByProductVariant(variant).getQuantity() >= x.getQuantity()) {
                item.setQuantity(x.getQuantity());
            } else {
                throw new ResourceNotFoundException("out of stock.");
            }
            item.setTotalPrice(variant.getPrice() * x.getQuantity());
            //here we are setting product level discount.
            Optional<Discount> discount = discountRepository.findDiscountByVariantId(variant.getId());
            if (discount.isPresent()) {
                if (discount.get().getWaiverMode() == WaiverMode.PERCENT) {
                    double couponDiscount = item.getTotalPrice() * discount.get().getDiscountValue() / 100;
                    item.setTotalPrice(item.getTotalPrice() - couponDiscount);
                    order.setDiscount(order.getDiscount() + couponDiscount);
                }
                if (discount.get().getWaiverMode() == WaiverMode.FIXED) {
                    double flatDiscount = discount.get().getDiscountValue() * item.getQuantity();
                    item.setTotalPrice(item.getTotalPrice() - flatDiscount);
                    order.setDiscount(order.getDiscount() + flatDiscount);
                }
            }
            order.setTotalAmount(order.getTotalAmount() + item.getTotalPrice());
            orderItemRepository.save(item);
            return item;
        }).toList();
        order.setOrderItems(list);

//        setting 5% discount if total amount is >= 1000 && < 2000
        if(order.getTotalAmount() >= 1000d && order.getTotalAmount() < 2000d){
            double orderLevelDiscount = order.getTotalAmount() * 0.05d;
            order.setDiscount(order.getDiscount() + orderLevelDiscount);
            order.setTotalAmount(order.getTotalAmount() - orderLevelDiscount);
        }

//        setting 10% discount if total amount is >= 2000 && < 5000
        else if (order.getTotalAmount() >= 2000d && order.getTotalAmount() < 5000d) {
            double orderLevelDiscount = order.getTotalAmount() * 0.10d;
            order.setDiscount(order.getDiscount() + orderLevelDiscount);
            order.setTotalAmount(order.getTotalAmount() - orderLevelDiscount);
            }

//        setting 15% discount if total amount is >= 5000
        else if (order.getTotalAmount() >= 5000d) {
                    double orderLevelDiscount = order.getTotalAmount() * 0.15d;
                    order.setDiscount(order.getDiscount() + orderLevelDiscount);
                    order.setTotalAmount(order.getTotalAmount() - orderLevelDiscount);
                }




        //        setting tax
        order.setTax(order.getTotalAmount() * 0.18d);
//        applying tax to the total amount
        order.setTotalAmount(order.getTotalAmount() + order.getTax());

        orderRepository.save(order);
//        managing inventory
        for (OrderItem item : list) {
            ProductVariant variant = item.getProductVariant();
            ProductInventory inventory = inventoryRepository.findProductInventoryByProductVariant(variant);
            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public List<OrderResponse> getAll() {

        List<Order> all = orderRepository.findAll();
        return all.stream().map(OrderResponse::new).toList();
    }
//

    @Override
    public OrderResponse getById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("invalid order ID"));
        return new OrderResponse(order);
    }


    @Override
    public void delete(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid order ID"));
        List<OrderItem> orderItems = order.getOrderItems();
        orderRepository.delete(order);
        for (OrderItem item : orderItems) {
            orderItemRepository.delete(orderItemRepository.findById
                    (item.getId()).orElseThrow(() -> new ResourceNotFoundException("invalid order item ID")));
        }
//        List<Long> list = orderItems.stream().map((x) -> x.getOrderItemId()).toList();
//        List<OrderItem> deletedItems = list.stream().map((x) -> orderItemRepository.findById(x)
//                .orElseThrow(() -> new ResourceNotFoundException("invalid Order Item id"))).toList();

    }

    @Override
    @Transactional
    public void updateOrder(Long id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("order id does not exist."));
        order.setUpdatedAt(LocalDateTime.now());
        if (request.getUserPhoneNumber() != null) {
            order.setUserPhoneNumber(request.getUserPhoneNumber());
            Customer customer = order.getCustomer();
            customer.setPhoneNumber(request.getUserPhoneNumber());
            customer.setEmail(request.getEmail());
            customerRepository.save(customer);
            List<Order> orders = orderRepository.findAllOrdersByCustomer(customer);
            if(!orders.isEmpty()){
                for(Order order1 : orders){
                    order1.setUserPhoneNumber(request.getUserPhoneNumber());
                }
                orderRepository.saveAll(orders);
            }

        }
        if (request.getCashAmount() != null) {
            order.setCashAmount(request.getCashAmount());
        }
        if (request.getOnlineAmount() != null) {
            order.setOnlineAmount(request.getOnlineAmount());
        }
        if (request.getPaymentMode() != null) {
            order.setPaymentMode(request.getPaymentMode());
        }
        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }
        if (request.getOrderItemRequests() != null) {
            //here we collected all the old order items.
            List<OrderItem> oldOrderItems = order.getOrderItems();



            order.setTotalAmount(0d);
            order.setDiscount(0d);
            //here we fetched all the requests for new orderItems.
            List<OrderItemRequest> orderItemRequests = request.getOrderItemRequests();

            //here we are creating and setting the newOrderItems in the order.
            List<OrderItem> newOrderItems = orderItemRequests.stream().map(a -> {
                OrderItem orderItem = new OrderItem();
                ProductVariant variant = productVariantRepository.findById(a.getVariantId()).orElseThrow(() -> new ResourceNotFoundException("variant id does not exist."));
                orderItem.setProductVariant(variant);
                orderItem.setProduct(variant.getProduct());

                //here we are managing the quantity of variants in inventory.
                ProductInventory inventory = inventoryRepository.findProductInventoryByProductVariant(variant);
                if (inventory.getQuantity() >= a.getQuantity()) {
                    orderItem.setQuantity(a.getQuantity());
                    inventory.setQuantity(inventory.getQuantity() - a.getQuantity());
                } else {
                    throw new ResourceNotFoundException("item is out of stock.");
                }
                orderItem.setUnitPrice(variant.getPrice());

                //This is the total of orderItem based on its quantity.
                orderItem.setTotalPrice(variant.getPrice()* a.getQuantity());

                Optional<Discount> discount = discountRepository.findDiscountByVariantId(variant.getId());
                if (discount.isPresent()){
                    if(discount.get().getWaiverMode() == WaiverMode.FIXED){
                        double flatDiscount = discount.get().getDiscountValue() * orderItem.getQuantity();
                        orderItem.setTotalPrice(orderItem.getTotalPrice() - flatDiscount);
                        order.setDiscount(order.getDiscount() + flatDiscount);

                    }
                    if(discount.get().getWaiverMode() == WaiverMode.PERCENT){
                        double coupenDiscount = orderItem.getTotalPrice() * discount.get().getDiscountValue() /100;
                        orderItem.setTotalPrice(orderItem.getTotalPrice() - coupenDiscount);
                        order.setDiscount(order.getDiscount() + coupenDiscount);
                    }
                }


                //This is the total of whole Order.
                order.setTotalAmount(order.getTotalAmount() + orderItem.getTotalPrice());
                orderItemRepository.save(orderItem);
                return orderItem;
            }).collect(Collectors.toCollection(ArrayList::new)); //stream ended here.
            //here we are setting the newOrderItems in Order.
            order.setOrderItems(newOrderItems);

            //here we are managing the inventory of variants which were used as oldOrderItems.
            for (OrderItem item : oldOrderItems) {
                ProductInventory inventory = inventoryRepository.findProductInventoryByProductVariant(item.getProductVariant());
                inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
                inventoryRepository.save(inventory);
                orderItemRepository.delete(item);

            }

//        setting 5% discount if total amount is >= 1000 && < 2000
            if(order.getTotalAmount() >=1000 && order.getTotalAmount() <2000){
                double orderLevelDiscount = order.getTotalAmount() * 0.05d;
                order.setDiscount(order.getDiscount()+ orderLevelDiscount);
                order.setTotalAmount(order.getTotalAmount() - orderLevelDiscount);
//        setting 10% discount if total amount is >= 2000 && <5000
            } else if (order.getDiscount() >=2000 && order.getDiscount() <5000) {
                double orderLevelDiscount = order.getTotalAmount() * 0.10d;
                order.setDiscount(order.getDiscount() + orderLevelDiscount);
                order.setTotalAmount(order.getTotalAmount() - orderLevelDiscount);
//        setting 15% discount if total amount is >= 5000
            } else if (order.getTotalAmount() >= 5000) {
                double orderLevelDiscount = order.getTotalAmount() * 0.15d;
                order.setDiscount(order.getDiscount() + orderLevelDiscount);
                order.setTotalAmount(order.getTotalAmount() - orderLevelDiscount);
            }

            //setting Tax.
            order.setTax(order.getTotalAmount() * 0.18d);
            order.setTotalAmount(order.getTotalAmount() + order.getTax());

            orderRepository.save(order);
//            for(OrderItem orderItem:newOrderItems){
//                ProductInventory inventory = inventoryRepository.findProductInventoryByProductVariant(orderItem.getProductVariant());
//               inventory.setQuantity(inventory.getQuantity()-orderItem.getQuantity());
//               inventoryRepository.save(inventory);
//            }
        }
    }

}

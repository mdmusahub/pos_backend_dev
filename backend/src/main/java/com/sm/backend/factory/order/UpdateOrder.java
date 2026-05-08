package com.sm.backend.factory.order;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.*;
import com.sm.backend.repository.*;
import com.sm.backend.request.OrderItemRequest;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.util.RequestType;
import com.sm.backend.util.TaxCategory;
import com.sm.backend.util.WaiverMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateOrder implements OrderOperation{

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductVariantRepository productVariantRepository;
    private final TaxRepository taxRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductInventoryRepository inventoryRepository;
    private final DiscountRepository discountRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.UPDATE;
    }

    @Override
    public OrderResponse execute(OrderRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
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
            Double gstSumPrice = 0d;
            //here we fetched all the requests for new orderItems.
            List<OrderItemRequest> orderItemRequests = request.getOrderItemRequests();

            //here we are creating and setting the newOrderItems in the order.
            List<OrderItem> newOrderItems = new ArrayList<>();
            for(OrderItemRequest a : orderItemRequests){
                OrderItem orderItem = new OrderItem();
                ProductVariant variant = productVariantRepository.findById(a.getVariantId()).orElseThrow(() -> new ResourceNotFoundException("variant id does not exist."));
                orderItem.setProductVariant(variant);
                orderItem.setProduct(variant.getProduct());

                Tax tax = new Tax();
                tax.setProductVariantPrice(variant.getPrice());
                tax.setGstPercent("0%");
                tax.setTaxPrice(0d);
                tax.setTaxQuantity(0L);
                tax.setTotalPrice(0d);

                Category category = orderItem.getProduct().getCategory().getParentId();

                if(category.getName().toUpperCase() .equals(TaxCategory.ELECTRONICS.toString())){
                    tax.setCategory(TaxCategory.ELECTRONICS);
                    tax.setGstPercent("18%");
                    tax.setTaxQuantity(tax.getTaxQuantity() + a.getQuantity());
                    tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.18 * tax.getTaxQuantity());
                    tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
                    tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
                    gstSumPrice += tax.getTaxPrice();
                }
                if(category.getName().toUpperCase() .equals(TaxCategory.GROCERY.toString())){
                    tax.setCategory(TaxCategory.GROCERY);
                    tax.setGstPercent("5%");
                    tax.setTaxQuantity(tax.getTaxQuantity() + a.getQuantity());
                    tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.05 * tax.getTaxQuantity());
                    tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
                    tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
                    gstSumPrice += tax.getTaxPrice();
                }
                if(category.getName().toUpperCase() .equals(TaxCategory.DAIRY.toString())){
                    tax.setCategory(TaxCategory.DAIRY);
                    tax.setGstPercent("5%");
                    tax.setTaxQuantity(tax.getTaxQuantity() + a.getQuantity());
                    tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.05 * tax.getTaxQuantity());
                    tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
                    tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
                    gstSumPrice += tax.getTaxPrice();
                }
                if (category.getName().toUpperCase() .equals(TaxCategory.SNACKS.toString())){
                    tax.setCategory(TaxCategory.SNACKS);
                    tax.setGstPercent("12%");
                    tax.setTaxQuantity(tax.getTaxQuantity() + a.getQuantity());
                    tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.12 * tax.getTaxQuantity());
                    tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
                    tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
                    gstSumPrice += tax.getTaxPrice();
                }
                taxRepository.save(tax);
                orderItem.setTax(tax);

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
                newOrderItems.add(orderItem);
            } //stream ended here.
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
            order.setTax(gstSumPrice);
            order.setTotalAmount(order.getTotalAmount() + order.getTax());

            orderRepository.save(order);
//            for(OrderItem orderItem:newOrderItems){
//                ProductInventory inventory = inventoryRepository.findProductInventoryByProductVariant(orderItem.getProductVariant());
//               inventory.setQuantity(inventory.getQuantity()-orderItem.getQuantity());
//               inventoryRepository.save(inventory);
//            }
        }
        return OrderResponse.builder()
                .message("Order updated successfully.")
                .build();
    }
}

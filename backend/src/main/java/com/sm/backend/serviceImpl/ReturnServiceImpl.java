package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.*;
import com.sm.backend.repository.*;
import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.request.ReturnQuantityRequest;
import com.sm.backend.response.ReturnOrderItemResponse;
import com.sm.backend.response.ReturnOrderResponse;
import com.sm.backend.service.EmailService;
import com.sm.backend.service.ReturnService;
import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnServiceImpl implements ReturnService {

    private final ReturnOrderItemRepository returnOrderItemRepository;
    private final ReturnOrderRepository returnOrderRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final EmailService emailService;

    @Autowired
    public ReturnServiceImpl(ReturnOrderItemRepository returnOrderItemRepository,
                             ReturnOrderRepository returnOrderRepository,
                             OrderRepository orderRepository1,
                             OrderItemRepository orderItemRepository,
                             ProductInventoryRepository productInventoryRepository,
                             ProductVariantRepository productVariantRepository,
                             EmailService emailService) {
        this.returnOrderItemRepository = returnOrderItemRepository;
        this.returnOrderRepository = returnOrderRepository;
        this.orderRepository = orderRepository1;
        this.orderItemRepository = orderItemRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.productVariantRepository = productVariantRepository;
        this.emailService = emailService;
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
        LocalDateTime orderDate = order.getOrderDate();
        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now(zoneId);
        String format = dateTime.format(dateTimeFormatter);
        Long id = order.getId();
        List<String> names = items.stream().map(x -> {
            return x.getProduct().getProductName()+" "+
                x.getProductVariant().getVariantName()+" "+x.getProductVariant().getVariantValue()+
                "\n Price : "+x.getProductVariant().getPrice()+"\n";}).toList();
        String email = order.getCustomer().getEmail();
            emailService.sendMail(email, "Blinkit",
                    "Request for Return of Order – "+id+"\n" +
                            "\n" +
                            "Dear Blenkit Customer Support,\n" +
                            "\n" +
                            "I hope this message finds you well.\n" +
                            "\n" +
                            "I would like to request a return for a product I purchased from your store. Here are the details:\n" +
                            "\n" +
                            "Order Number: "+id+"\n" +
                            "\n" +
                            "Product Name: "+names+"\n" +
                            "\n" +
                            "Date : "+format+"\n" +
                            "\n" +
                            "Reason for Return: [Brief Reason – e.g., wrong item received, defective product, etc.]\n" +
                            "\n" +
                            "The product is unused and in its original packaging. Kindly guide me through the return process and let me know the next steps. I would appreciate it if you could also confirm the refund timeline.\n" +
                            "\n" +
                            "Looking forward to your prompt response.\n" +
                            "\n" +
                            "Thank you,\n" +
                            email);

    }



    
    @Override
    public ReturnOrderResponse getById(Long id) {
        ReturnOrder returnOrder = returnOrderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("ReturnOrder id not found"));
        List<ReturnOrderItem> returnOrderItems = returnOrderItemRepository.findAllReturnOrderItemsByOrder(returnOrder.getOrder());
        return new ReturnOrderResponse(returnOrder,returnOrderItems);
    }

    @Override
    public void deleteById(Long id) {
        ReturnOrder returnOrder = returnOrderRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Id not found"));
        Order order = returnOrder.getOrder();
        List<ReturnOrderItem> returnOrderItem = returnOrderItemRepository.findAllReturnOrderItemsByOrder(order);
        returnOrderItemRepository.deleteAll(returnOrderItem);
        returnOrderRepository.delete(returnOrder);
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

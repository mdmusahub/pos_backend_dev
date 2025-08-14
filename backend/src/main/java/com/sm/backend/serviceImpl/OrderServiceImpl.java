package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.*;
import com.sm.backend.repository.*;
import com.sm.backend.request.OrderItemRequest;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.service.EmailService;
import com.sm.backend.service.OrderService;
import com.sm.backend.util.TaxCategory;
import com.sm.backend.util.WaiverMode;
import com.sm.backend.utility.OrderStatus;
import com.sm.backend.utility.PaymentMode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private final TaxRepository taxRepository;
    private final ReturnOrderRepository returnOrderRepository;
    private final ReturnOrderItemRepository returnOrderItemRepository;
    private final EmailService emailService;

    @Autowired
    public OrderServiceImpl(ProductInventoryRepository inventoryRepository,
                            OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            ProductVariantRepository productVariantRepository,
                            CustomerRepository customerRepository,
                            DiscountRepository discountRepository,
                            TaxRepository taxRepository,
                            ReturnOrderRepository returnOrderRepository,
                            ReturnOrderItemRepository returnOrderItemRepository,
                            EmailService emailService) {
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productVariantRepository = productVariantRepository;
        this.customerRepository = customerRepository;
        this.discountRepository = discountRepository;
        this.taxRepository = taxRepository;
        this.returnOrderRepository = returnOrderRepository;
        this.returnOrderItemRepository = returnOrderItemRepository;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<?> createOrder(OrderRequest request) throws ResourceNotFoundException {
        //here we're getting and setting the values of order table.
        Order order = new Order();
        Double gstSumPrice = 0d;
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
        List<OrderItem> list = new ArrayList<>();
//                orderItemRequests.stream().map(x -> {
        for (OrderItemRequest x : orderItemRequests) {
            OrderItem item = new OrderItem();

            ProductVariant variant = productVariantRepository.findById
                            (x.getVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("invalid variant ID."));

            item.setProductVariant(variant);
            item.setProduct(variant.getProduct());
            item.setUnitPrice(variant.getPrice());

            // Category base GST
            Tax tax = new Tax();
            tax.setProductVariantPrice(item.getUnitPrice());
            tax.setGstPercent("0%");
            tax.setTaxQuantity(0L);
            tax.setTaxPrice(0d);
            tax.setTotalPrice(0d);

            Category category = item.getProductVariant().getProduct().getCategory().getParentId();

            if (category.getName().toUpperCase().equals(TaxCategory.ELECTRONICS.toString())) {
                tax.setCategory(TaxCategory.ELECTRONICS);
                tax.setGstPercent("18%");
                tax.setTaxQuantity(tax.getTaxQuantity() + x.getQuantity());
                tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.18d * tax.getTaxQuantity());
                tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
                tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
                gstSumPrice += tax.getTaxPrice();
            }
            if (category.getName().toUpperCase().equals(TaxCategory.SNACKS.toString())) {
                tax.setCategory(TaxCategory.SNACKS);
                tax.setGstPercent("12%");
                tax.setTaxQuantity(tax.getTaxQuantity() + x.getQuantity());
                tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.12d * tax.getTaxQuantity());
                tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
                tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
                gstSumPrice += tax.getTaxPrice();
            }
            if (category.getName().toUpperCase().equals(TaxCategory.DAIRY.toString())) {
                tax.setCategory(TaxCategory.DAIRY);
                tax.setGstPercent("5%");
                tax.setTaxQuantity(tax.getTaxQuantity() + x.getQuantity());
                tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.05d * tax.getTaxQuantity());
                tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
                tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
                gstSumPrice += tax.getTaxPrice();
            }
            if (category.getName().toUpperCase().equals(TaxCategory.GROCERY.toString())) {
                tax.setCategory(TaxCategory.GROCERY);
                tax.setGstPercent("5%");
                tax.setTaxQuantity(tax.getTaxQuantity() + x.getQuantity());
                tax.setTaxPrice(tax.getTaxPrice() + tax.getProductVariantPrice() * 0.05d * tax.getTaxQuantity());
                tax.setTotalPrice(tax.getTotalPrice() + tax.getProductVariantPrice() * tax.getTaxQuantity());
                tax.setTotalPrice(tax.getTotalPrice() + tax.getTaxPrice());
                gstSumPrice += tax.getTaxPrice();
            }
            taxRepository.save(tax);
            item.setTax(tax);
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
            list.add(item);
        }
        order.setOrderItems(list);

//        setting 5% discount if total amount is >= 1000 && < 2000
        if (order.getTotalAmount() >= 1000d && order.getTotalAmount() < 2000d) {
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
        order.setTax(gstSumPrice);
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
        if (request.getPaymentMode() != null) {
            order.setPaymentMode(request.getPaymentMode());
        }


//        } else if (request.getPaymentMode() == PaymentMode.UPI) {
//            order.setPaymentMode(PaymentMode.UPI);
//            billResponse.setOrderResponse(new OrderResponse(order));
//            try {
//                UpiPayment upiPayment = new UpiPayment();
//                upiPayment.setUpiId("9644572863@kotak811");
//                upiPayment.setPayeeName("Mohsin");
//                upiPayment.setAmount(order.getTotalAmount().toString());
//                String upiUrl = String.format("upi://pay?pa=%s&pn=%s&am=%s&cu=INR",
//                        upiPayment.getUpiId(),
//                        URLEncoder.encode(upiPayment.getPayeeName(), StandardCharsets.UTF_8),
//                        upiPayment.getAmount());
//
//                QRCodeWriter qrCodeWriter = new QRCodeWriter();
//                BitMatrix bitMatrix = qrCodeWriter.encode(upiUrl, BarcodeFormat.QR_CODE, 350, 350);
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
//                billResponse.setQrCode(outputStream.toByteArray()); //outputStream.toByteArray();
//            } catch (UnsupportedEncodingException e) {
//                throw new RuntimeException(e);
//            } catch (WriterException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
        List<String> collect = list.stream().map(x -> x.toString()).collect(Collectors.toList());

        emailService.sendMail(request.getEmail(),"Blinkit "," Thanks for Shopping with Blinkit \n"+
                " \n Date = "+order.getOrderDate()+" \n Phone Number = "+request.getUserPhoneNumber()+
                " \n \n Item = "+collect +" \n \n Payment Mode = "+request.getPaymentMode()+
                " \n Discount = "+order.getDiscount()+ " \n Tax = "+order.getTax()+
                " \n Total Amount = "+order.getTotalAmount());



           return ResponseEntity.ok(new OrderResponse(order));

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
        Optional <ReturnOrder> returnOrderByOrder = returnOrderRepository.findReturnOrderByOrder(order);
        if (returnOrderByOrder.isPresent()) {
            returnOrderRepository.delete(returnOrderByOrder.get());
        }
        List<ReturnOrderItem> allReturnOrderItemsByOrder = returnOrderItemRepository.findAllReturnOrderItemsByOrder(order);
        returnOrderItemRepository.deleteAll(allReturnOrderItemsByOrder);
        List<OrderItem> orderItems = order.getOrderItems();
        orderRepository.delete(order);
        orderItemRepository.deleteAll(orderItems);
//        for (OrderItem item : orderItems) {
//            orderItemRepository.delete(orderItemRepository.findById
//                    (item.getId()).orElseThrow(() -> new ResourceNotFoundException("invalid order item ID")));
//        }
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
    }

}

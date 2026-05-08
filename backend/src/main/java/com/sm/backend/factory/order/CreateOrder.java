package com.sm.backend.factory.order;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.*;
import com.sm.backend.repository.*;
import com.sm.backend.request.OrderItemRequest;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.service.EmailService;
import com.sm.backend.util.RequestType;
import com.sm.backend.util.TaxCategory;
import com.sm.backend.util.WaiverMode;
import com.sm.backend.utility.OrderStatus;
import com.sm.backend.utility.PaymentMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateOrder implements OrderOperation{

    private final OrderItemRepository repository;
    private final OrderRepository orderRepository;
    private final ProductVariantRepository productVariantRepository;
    private final TaxRepository taxRepository;
    private final CustomerRepository customerRepository;
    private final DiscountRepository discountRepository;
    private final EmailService emailService;
    private final ProductInventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.CREATE;
    }

    @Override
    public OrderResponse execute(OrderRequest request) {
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

            Category category =
                    item.getProductVariant().getProduct().getCategory();

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
//                    double couponDiscount = item.getTotalPrice() * discount.get().getDiscountValue();
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

        //        setting tax
        order.setTax(gstSumPrice);
//        applying tax to the total amount
        order.setTotalAmount(order.getTotalAmount() + order.getTax());

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


//        //        setting tax
//        order.setTax(gstSumPrice);
////        applying tax to the total amount
//        order.setTotalAmount(order.getTotalAmount() + order.getTax());

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

        emailService.sendMail(request.getEmail(),"Blinket ",
                "Thank you so much for shopping with us!  \n" +
                        "We’re excited to let you know that your order has been received successfully.  \n" +
                        "\n" +
                        "Our team is preparing everything with care to make sure you get the best experience with our products.  \n" +
                        "\n" +
                        "If you have any questions or need any assistance regarding your order, feel free to reach out to us anytime.  \n" +
                        "We’re always happy to help.  \n" +
                        "\n" +
                        "Once again, thank you for choosing us.  \n" +
                        "We look forward to serving you again in the future!  \n" +
                        "\n" +
                        "Warm Regards,  \n" +
                        "Blinket"+
                        " \n Date :  "+order.getOrderDate()+" \n Phone Number :  "+request.getUserPhoneNumber()+
                        " \n \n Item :  "+collect +" \n \n Payment Mode :  "+request.getPaymentMode()+
                        " \n Discount :  "+order.getDiscount()+ " \n Tax :  "+order.getTax()+
                        " \n Total Amount :  "+order.getTotalAmount());

        return OrderResponse.builder()
                .message("order created successfully")
                .build();
    }
}

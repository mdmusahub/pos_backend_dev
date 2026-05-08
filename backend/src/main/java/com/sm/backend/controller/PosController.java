package com.sm.backend.controller;

import com.sm.backend.configration.CustomUserDetailService;
import com.sm.backend.configration.JwtUtil;
import com.sm.backend.factory.category.CategoryFactory;
import com.sm.backend.factory.category.CategoryOperation;
import com.sm.backend.factory.customer.CustomerFactory;
import com.sm.backend.factory.customer.CustomerOperation;
import com.sm.backend.factory.discount.DiscountFactory;
import com.sm.backend.factory.discount.DiscountOperation;
import com.sm.backend.factory.inventory.InventoryFactory;
import com.sm.backend.factory.inventory.InventoryOperation;
import com.sm.backend.factory.order.OrderFactory;
import com.sm.backend.factory.order.OrderOperation;
import com.sm.backend.factory.product.ProductFactory;
import com.sm.backend.factory.product.ProductOperation;
import com.sm.backend.factory.productVariant.VariantFactory;
import com.sm.backend.factory.productVariant.VariantOperation;
import com.sm.backend.factory.returnOrder.ReturnFactory;
import com.sm.backend.factory.returnOrder.ReturnOperation;
import com.sm.backend.factory.tax.TaxFactory;
import com.sm.backend.factory.tax.TaxOperation;
import com.sm.backend.model.User;
import com.sm.backend.repository.UserRepository;
import com.sm.backend.request.*;
import com.sm.backend.response.*;
import com.sm.backend.service.EmailService;
import com.sm.backend.service.ProductService;
import com.sm.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
//@RequestMapping("/product")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Log4j2
//    ,allowedHeaders = "*",
//    methods = {RequestMethod.GET,
//            RequestMethod.POST,
//            RequestMethod.PUT,
//            RequestMethod.DELETE,
//            RequestMethod.OPTIONS})
public class PosController {
    private final ProductService service;

    private final ProductFactory factory;

    private final InventoryFactory inventoryFactory;

    private final CategoryFactory categoryFactory;

    private final OrderFactory orderFactory;

    private final CustomerFactory customerFactory;

    private final VariantFactory variantFactory;

    private final ReturnFactory returnFactory;

    private final TaxFactory taxFactory;

    private final DiscountFactory discountFactory;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailService userDetailService;

    private final JwtUtil jwtUtil;

    private final EmailService emailService;

    private final UserRepository userRepository;



//    @PostMapping("/create")
//    public void createProduct(@RequestBody ProductRequest request) {
//        service.createProduct(request);
//    }
//
//    @GetMapping("/getById/{productId}")
//    public ResponseEntity<ProductResponse> getById(@PathVariable Long productId) {
//        try {
//            return new ResponseEntity<>(service.getById(productId),HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/getAll")
//    public ResponseEntity<List<ProductResponse>> getAll() {
//        try {
//            return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @PutMapping("/update/{productId}")
//    public ResponseEntity<?>update(@RequestBody ProductRequest request,@PathVariable Long productId ){
//        try {
//            return ResponseHandler.responseHandler("Product updated successfully",
//                    HttpStatus.OK,service.update(request,productId));
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//@DeleteMapping("/delete/{productId}")
//public void delete(@PathVariable Long productId){
//       service.delete(productId);
//
//}
//@GetMapping("/getAllDetails/{id}")
//    public ResponseEntity<ProductVariantInventoryResponse> getAllProductDetails(@PathVariable Long id){
//        try {
//            return new ResponseEntity<>(service.getAllProductDetails(id),HttpStatus.OK);
//        } catch (Exception e) {
//            throw new ResourceNotFoundException(e.getMessage());
//        }
//}
//@PutMapping("/updateAllDetails/{id}")
//    public void updateAllDetails(@RequestBody ProductUpdateRequest request, @PathVariable Long id){
//        service.updateAllDetails(request,id);

//}
    @PostMapping("/product")
    public ProductResponse productFactory(@RequestBody ProductRequest request){
        ProductOperation operation = factory.getOperation(request.getRequestType());
        return operation.execute(request);
    }

    @PostMapping("/inventory")
    public ProductInventoryResponse
    inventoryFactory(@RequestBody ProductInventoryRequest request) {
        InventoryOperation operation =
                inventoryFactory.getOperation(request.getRequestType());
        return operation.execute(request);
    }

    @PostMapping("/category")
    public CategoryResponse categoryFactory(@RequestBody CategoryRequest request) {
        CategoryOperation operation =
                categoryFactory.getOperation(request.getRequestType());
        return operation.execute(request);
    }

    @PostMapping("/order")
    public OrderResponse orderFactory(@RequestBody OrderRequest request) {
        OrderOperation operation =
                orderFactory.getOperation(request.getRequestType());
        return operation.execute(request);
    }

    @PostMapping("/customer")
    public CustomerResponse customerFactory(@RequestBody CustomerRequest request) {
        CustomerOperation operation =
                customerFactory.getOperation(request.getRequestType());
        return operation.execute(request);
    }

    @PostMapping("/variant")
    private ProductVariantResponse variantFactory(@RequestBody ProductVariantRequest request){

        VariantOperation operation = variantFactory.getOperation(request.getRequestType());
        return operation.execute(request);
    }

    @PostMapping("/returnOrder")
    public ReturnOrderResponse returnFactory(@RequestBody ReturnOrderRequest request){

        ReturnOperation operation = returnFactory.getOperation(request.getRequestType());
        return operation.execute(request);
    }

    @PostMapping("/tax")
    public TaxResponse taxFactory(@RequestBody TaxRequest request){

        TaxOperation operation = taxFactory.getOperation(request.getRequestType());
        return operation.execute(request);
    }

    @PostMapping("/discount")
    private DiscountResponse discountFactory(@RequestBody DiscountRequest request) {
        DiscountOperation operation =
                discountFactory.getOperation(request.getRequestType());
        return operation.execute(request);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp (@RequestBody UserRequest request){
        userService.signUp(request);
        return ResponseEntity.ok(UserResponse.builder()
                .message("User registered successfully")
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login (@RequestBody UserRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            userDetailService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(request.getEmail());
            log.info("Jwt "+ token);
//            String email = request.getEmail();
//            String password = request.getPassword();
            Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
            String name = byEmail.get().getName();

            // Date and Time Shift bas Set
            ZoneId zoneId = ZoneId.of("Asia/Kolkata");
            LocalDate date = LocalDate.now(zoneId);
            LocalTime startTime = LocalTime.now(zoneId);
            LocalTime endTime = startTime.plusHours(5);

            // Date and Time Formatted
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
            String formattedStart = startTime.format(timeFormatter);
            String formattedEnd = endTime.format(timeFormatter);

            emailService.sendMail(request.getEmail(), "Blinket","Hello "+name+"\n" +
                    "Thanks for signing up for your shift on "+ date +" from "+ formattedStart +" to "+ formattedEnd +". We appreciate your commitment and look forward to having you on during that time.\n" +
                    "\n" +
                    "If anything changes or you have questions, don’t hesitate to reach out.\n" +
                    "\n" +
                    "See you then!\n" +
                    "\n" +
                    "Best,\n" +
                    name);
            return ResponseEntity.ok(UserResponse.builder()
                    .message("Login successful")
                    .token(token)
                    .build());
        } catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken",e);
            return ResponseEntity.badRequest().body(UserResponse.builder()
                    .message("Invalid email or password")
                    .build());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout (@RequestHeader ("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        userService.addToBlacklist(token);
        return ResponseEntity.ok("Logout Successfully");
    }
}

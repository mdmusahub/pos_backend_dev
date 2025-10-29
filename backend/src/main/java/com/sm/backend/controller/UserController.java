package com.sm.backend.controller;

import com.sm.backend.configration.CustomUserDetailService;
import com.sm.backend.configration.JwtUtil;
import com.sm.backend.exceptionalHandling.EmailNotFoundException;
import com.sm.backend.model.User;
import com.sm.backend.repository.UserRepository;
import com.sm.backend.request.UserRequest;
import com.sm.backend.service.EmailService;
import com.sm.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    private final UserService userService;
    private final CustomUserDetailService userDetailService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserService userService,
                          CustomUserDetailService userDetailService,
                          JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager,
                          EmailService emailService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public String signUp (@RequestBody User user){
         userService.signUp(user);
        return "User Registered Successfully";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody UserRequest request){
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
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken",e);
            return new ResponseEntity<>("Incorrect Email or Password ",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll (){
        ResponseEntity<?> all = userService.findAll();
        return ResponseEntity.ok(all);
    }
    @PutMapping("/updateEmail/{email}")
    public String updateByEmail (@PathVariable String email,@RequestBody User user){
        userService.updateByPassword(email,user);
        return "Update Password Successfully";
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout (@RequestHeader ("Authorization") String token){
        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }
        userService.addToBlacklist(token);
        return ResponseEntity.ok("Logout Successfully");
    }
    @PutMapping("/getName")
    public String getByName (@RequestHeader ("Authorization") String token, @RequestBody String name){
        if(token.startsWith("Bearer")){
            token = token.substring(7);
        }
        return userService.getByName(token,name);
    }
}

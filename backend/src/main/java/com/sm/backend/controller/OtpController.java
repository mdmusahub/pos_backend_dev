package com.sm.backend.controller;

import com.sm.backend.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OtpController {
    private final OtpService otpService;
    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }


    @GetMapping("/sendOtp/{email}")
    public String sendOtp (@PathVariable String email){
        otpService.generateOtp(email);
        return "Send OTP Email";
//                userService.sendOtpEmail(email);
//            return "OTP sent to "+email+" "+sendOtp;
//            return "Email is null";
    }
    @PutMapping("/checkOtp/{email}")
    public String checkOtp (@PathVariable String email, @RequestBody String sendOtp){
        System.out.println(sendOtp);
        String substring1 = sendOtp.substring(10, 16);
        System.out.println(substring1);
       return otpService.checkOtp(email,substring1);
    }
}

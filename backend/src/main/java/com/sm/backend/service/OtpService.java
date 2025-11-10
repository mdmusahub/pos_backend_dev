package com.sm.backend.service;

public interface OtpService {
    void generateOtp(String email);
    String checkOtp (String email, String sendOtp);
}

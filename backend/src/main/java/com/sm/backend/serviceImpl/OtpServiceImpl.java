package com.sm.backend.serviceImpl;

import com.sm.backend.model.Otp;
import com.sm.backend.model.User;
import com.sm.backend.repository.OtpRepository;
import com.sm.backend.repository.UserRepository;
import com.sm.backend.service.EmailService;
import com.sm.backend.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public OtpServiceImpl(OtpRepository otpRepository,
                          UserRepository userRepository,
                          EmailService emailService) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }


    @Override
    public void generateOtp(String email) {
        if (email != null) {
            Optional<User> byEmail = userRepository.findByEmail(email);
            if (byEmail.isPresent()) {
                Otp otp = new Otp();
                Random random = new Random();
                int sendOtp = 100000 + random.nextInt(900000);
                otp.setEmail(email);
                otp.setOtp(String.valueOf(sendOtp));
                otpRepository.save(otp);
                emailService.sendMail(email, "Your OTP Code", "Your OTP is " + sendOtp + "\n" +
                        "Valid for 5 minutes");
                System.out.println("OTP Email Sent Successfully!");
                System.out.println(sendOtp);
            }
        }
    }

    // Scheduled run every 1 min
    @Scheduled(fixedRate = 60000)
    public String deleteOtp() {
        List<Otp> all = otpRepository.findAll();

        for (Otp checkOtp : all) {
            // every 5 min delete data
            LocalTime endTime = checkOtp.getGeneratedAt().toLocalTime().plusMinutes(5);
            if (LocalTime.now().isAfter(endTime)) {
                otpRepository.delete(checkOtp);
            }
        }
        return "scheduler is running";
    }
    public String checkOtp (String email, String sendOtp){
        Optional <Otp> byEmail = otpRepository.findByEmail(email);
        if (byEmail.isPresent()){
            String otp = byEmail.get().getOtp();
            if(otp.equals(sendOtp)){
                return "OTP Matched Successfully";
            }else {
                return "Wrong OTP";
            }
        }else {
            return "OTP is expired";
        }

    }
}
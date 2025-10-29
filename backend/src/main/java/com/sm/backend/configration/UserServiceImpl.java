package com.sm.backend.configration;

import com.sm.backend.exceptionalHandling.EmailNotFoundException;
import com.sm.backend.exceptionalHandling.UserAlreadyExistsException;
import com.sm.backend.model.Jwt;
import com.sm.backend.model.User;
import com.sm.backend.repository.JwtRepository;
import com.sm.backend.repository.UserRepository;
import com.sm.backend.service.EmailService;
import com.sm.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final Set<String> blacklist = new HashSet<>();
    private final JwtUtil jwtUtil;
    private final JwtRepository jwtRepository;


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void signUp(User user) {
        User user1 = new User();
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("Already Exists " + user.getEmail());
        } else {
            user1.setEmail(user.getEmail());
            user1.setName(user.getName());
            user1.setPassword(passwordEncoder().encode(user.getPassword()));
            user1.setRole(user.getRole().toUpperCase().replace("ROLE_", ""));
            userRepository.save(user1);
            emailService.sendMail(user.getEmail(), "Blinket", "Hello " + user.getName() + " \n" +
                    "\n" +
                    "We're thrilled to have you on board! Your skills and experience will be a valuable addition to our team, and we’re excited to see the great things we’ll accomplish together.\n" +
                    "\n" +
                    "Please take a moment to complete your employee profile and sign any necessary onboarding documents [insert link or instructions, if applicable]. If you have any questions, feel free to reach out—we're here to support you every step of the way.\n" +
                    "\n" +
                    "Welcome aboard!\n" +
                    "\n" +
                    "Best regards,\n" +
                    "Blinket");
        }
    }
    // Scheduled run every 1 min
    @Scheduled(fixedRate = 60000)
    public String jwtTokenDelete (){
        List<Jwt> all = jwtRepository.findAll();
        for(Jwt list : all){
            LocalTime time = list.getGeneratedAt().toLocalTime().plusHours(7);
//            LocalTime time = list.getGeneratedAt().toLocalTime().plusMinutes(1);
            if(LocalTime.now().isAfter(time)){
                jwtRepository.delete(list);

            }
        }
        return "scheduler is running";
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @Override
    public void addToBlacklist(String token) {
        String email = jwtUtil.extractUserName(token);
        blacklist.add(token);
        Jwt jwt = new Jwt();
        jwt.setJwtToken(token);
        jwtRepository.save(jwt);
        ZonedDateTime logoutTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        String formattedTime = logoutTime.format(DateTimeFormatter.ofPattern("dd mm yy, hh:mm a z"));
        emailService.sendMail(email, "Logout Confirmation","You have successfully logged out on " + formattedTime + ".\n" +
                "If this wasn't you, please contact support immediately.\n\n" +
                "Stay safe,\n" +
                "Team Blinket");
    }

//    public Boolean isBlacklisted(String token) {
//        return blacklist.contains(token);
//    }


    @Override
    public void updateByPassword(String email, User request) {
        Optional<User> emailOptional = userRepository.findByEmail(email);
        if (emailOptional.isPresent()) {
            User user = emailOptional.get();
            if (request.getName() != null) {
                user.setName(request.getName());
            }

            if (request.getPassword() != null) {
                user.setPassword(passwordEncoder().encode(request.getPassword()));

                String name = user.getName(); // User ka naam
                // Send email
                emailService.sendMail(user.getEmail(), "Password Updated Successfully", "Hello " + name + ",\n\n" +
                        "Your password has been successfully updated.\n" +
                        "If you did not request this change, please contact support immediately.\n\n" +
                        "Stay secure,\n" +
                        "Team Blinket");

                userRepository.save(user);
            } else {
                throw new EmailNotFoundException("Email not Found " + email);
            }

        }
    }

    public String getByName (String token,String name){
        String email = jwtUtil.extractUserName(token);
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            User user = byEmail.get();
            user.setName(name);
            userRepository.save(user);
            return "Update Successfully";
        }else {
            throw new EmailNotFoundException("Email is Invalid");
        }
    }

}

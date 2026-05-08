package com.sm.backend.configration;

import com.sm.backend.exceptionalHandling.EmailNotFoundException;
import com.sm.backend.exceptionalHandling.UserAlreadyExistsException;
import com.sm.backend.model.Jwt;
import com.sm.backend.model.User;
import com.sm.backend.repository.JwtRepository;
import com.sm.backend.repository.UserRepository;
import com.sm.backend.request.UserRequest;
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
    public void signUp(UserRequest request) {
        User user1 = new User();
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("Already Exists " + request.getEmail());
        } else {
//            Scanner inp = new Scanner(System.in);
//            String email,password;
//            email = inp.nextLine();
//            password = inp.nextLine();
//            user1.setEmail(email);
//            user1.setPassword(passwordEncoder().encode(password));
            user1.setEmail(request.getEmail());
            user1.setPassword(passwordEncoder().encode(request.getPassword()));
            user1.setName(request.getName());
//            user1.setRole(user.getRole().toUpperCase().replace("ROLE_", ""));
            user1.setRole("ADMIN");
            userRepository.save(user1);
            emailService.sendMail(request.getEmail(), "Registration Confirmation", "Hello " + request.getName() + ",\n\n" +
                    "Thank you for registering! Your account has been successfully created.\n" +
                    "If you did not register for this account, please contact support immediately.\n\n" +
                    "Welcome aboard");
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
                emailService.sendMail(user.getEmail(), "Password Updated " +
                        "Successfully", "Hello " + name + ",\n\n" +
                        "Your password has been successfully updated.");

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

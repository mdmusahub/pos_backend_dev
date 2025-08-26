package com.sm.backend.configration;

import com.sm.backend.exceptionalHandling.EmailNotFoundException;
import com.sm.backend.exceptionalHandling.UserAlreadyExistsException;
import com.sm.backend.model.User;
import com.sm.backend.repository.UserRepository;
import com.sm.backend.request.UserRequest;
import com.sm.backend.service.EmailService;
import com.sm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final Set<String> blacklist = new HashSet<>();
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EmailService emailService){
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void signUp(User user) {
        User user1 = new User();
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()){
           throw new UserAlreadyExistsException("Already Exists "+ user.getEmail());
        }else {
            user1.setEmail(user.getEmail());
            user1.setName(user.getName());
            user1.setPassword(passwordEncoder().encode(user.getPassword()));
            user1.setRole(user.getRole().toUpperCase().replace("ROLE_",""));
            userRepository.save(user1);
            emailService.sendMail(user.getEmail(), "Blinket","Hello "+user.getName()+" \n" +
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

    @Override
    public ResponseEntity<?> findAll() {
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @Override
    public void updateByPassword(String email,User request) {
        Optional<User> emailOptional = userRepository.findByEmail(email);
        if(emailOptional.isPresent()){
            User user = emailOptional.get();
            if(request.getName() !=null){
                user.setName(request.getName());
            }
            if (request.getPassword() != null){
                user.setPassword(passwordEncoder().encode(request.getPassword()));

                String name = user.getName(); // User ka naam
                // Send email
                emailService.sendMail(user.getEmail(), "Password Updated Successfully", "Hello " + name + ",\n\n" +
                        "Your password has been successfully updated.\n" +
                        "If you did not request this change, please contact support immediately.\n\n" +
                        "Stay secure,\n" +
                        "Team Blinket");

                userRepository.save(user);
            }else {
                throw new EmailNotFoundException("Email not Found "+ request.getEmail());
            }
        }

    }

    @Override
    public void addToBlacklist(String token ) {
        blacklist.add(token);
    }
    public Boolean isBlacklisted (String token){
        return blacklist.contains(token);
    }

}

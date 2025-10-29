package com.sm.backend.service;

import com.sm.backend.model.User;
import com.sm.backend.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface UserService {

    void signUp (User user);
    ResponseEntity<?> findAll ();
    void updateByPassword (String email,User user);
    void addToBlacklist (String token);
//    Boolean isBlacklisted (String token);
    String getByName (String token,String name);

}

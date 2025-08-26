package com.sm.backend.response;

import com.sm.backend.model.User;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String name ;
    private String password;
    private String role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}

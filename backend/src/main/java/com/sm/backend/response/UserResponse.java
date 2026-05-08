package com.sm.backend.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String email;
    private String name ;
    private String password;
    private String role;
    private String message;
    private String token;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}

package com.sm.backend.repository;

import java.util.Optional;
import com.sm.backend.model.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
public interface JwtRepository extends JpaRepository<Jwt,Long> {
    Optional<Jwt> findByJwtToken (String token);
}

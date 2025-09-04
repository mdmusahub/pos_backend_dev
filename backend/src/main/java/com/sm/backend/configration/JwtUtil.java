package com.sm.backend.configration;

import com.sm.backend.repository.OtpRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    @Value("${secret.key}")
    private String SECRECT_KEY;




    private SecretKey getSigningKey() {
        log.debug("Generating signing key from secret.");
        return Keys.hmacShaKeyFor(SECRECT_KEY.getBytes());
    }

    public Claims extractAllClaims(String token) {
        try {
            log.debug("Extracting all claims from token: {}", token);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.debug("Claims extracted: {}", claims);
            return claims;
        } catch (JwtException e) {
            log.error("Failed to extract claims from token: {}", token, e);
            throw e;
        }
    }

    public String extractUserName(String token) {
        log.debug("Extracting email from token.");
        String email = extractAllClaims(token).getSubject();
        log.debug("Username extracted: {}", email);
        return email;
    }

    public Date extractExpiration(String token) {
        log.debug("Extracting expiration from token.");
        Date expiration = extractAllClaims(token).getExpiration();
        log.debug("Extracting Date : {} ", expiration);
        return expiration;
    }

    private Boolean isTokenExpired(String token) {
        boolean expired = extractExpiration(token).before(new Date());
        log.debug("Is token expired: {}", expired);
        return expired;
    }

    public String createToken(Map<String, Object> claims, String subject) {
        log.debug("Creating token for subject: {}, with claims: {}", claims, subject);
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5);
        log.debug("Issued at : {}, Expiration : {}", issuedAt, expiration);

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration) // 5 hours
                .signWith(getSigningKey())
                .compact();
        log.debug("Token Create : {}", token);
        return token;
    }

    public String generateToken(String userName) {
        log.debug("Generating token for user: {}", userName);
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    public Boolean validateToken(String token) {
        log.debug("Validating token.");
        try {
            boolean valid = !isTokenExpired(token);
            log.debug("Token valid: {}", valid);
            return valid;
        } catch (Exception e) {
            log.debug("Token validation failed.", e);
            return false;
        }

    }

}

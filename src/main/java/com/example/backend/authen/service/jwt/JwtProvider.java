package com.example.backend.authen.service.jwt;

import com.example.backend.authen.service.userdetail.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.security.SignatureException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret1}")
    private String jwtSecret;

    @Value("${jwt.secret2}")
    private String passwordResetJwtSecret ;


    private int jwtExpiration = 86400;

    public String generateJwtToken(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }
    public String getUserNameFromJwtToken(String token) {

        String userName = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }


    public String generatePasswordResetToken(String username) {
        // Tạo một JWTBuilder
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + TimeUnit.MINUTES.toMillis(3)))
                .signWith(SignatureAlgorithm.HS512,passwordResetJwtSecret);

        // Tạo token
        String token = jwtBuilder.compact();
        return token;
    }

    public String getUserNameFromResetToken(String token) {

        String userName = Jwts.parser()
                .setSigningKey(passwordResetJwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }

    public boolean validatePasswordResetToken(String token) {
        try {
            Jwts.parser().setSigningKey(passwordResetJwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("Invalid or expired JWT: " + e.getMessage());
        }
        return false;
    }

}

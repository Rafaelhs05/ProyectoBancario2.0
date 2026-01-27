package app.auth.application.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.auth.web.dto.ResponseUser;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    private Key secret;

    @Value("${jwt.secret}")
    String secretString;

    @PostConstruct
    protected void init() {

        secret = Keys.hmacShaKeyFor(secretString.getBytes());
    }

    public String createToken(ResponseUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", authUser.getRole());
        claims.put("username", authUser.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * public String createInteralToken(String servicename){
     * 
     * Map<String, Object> claims = new HashMap<>();
     * claims.put("role", "internal");
     * claims.put("username", servicename);
     * 
     * return Jwts.builder()
     * .setClaims(claims)
     * .setSubject(servicename)
     * .setIssuedAt(new Date())
     * .setExpiration(new Date(System.currentTimeMillis() + 3600000))
     * .signWith(secret, SignatureAlgorithm.HS256)
     * .compact();
     * }
     */

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return "bad token";
        }
    }
}

package com.phone.book.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

     private static String SECRET_KEY = "Secret" ;
       
     public String extractEmail(String token)
     {
         return extractClaim(token , Claims::getSubject);
     }

    private <T> T extractClaim(String token, Function<Claims, T> claimsresolver) {
        final Claims claims = extractAllClaims(token);
        return claimsresolver.apply(claims);
    }

     private Claims extractAllClaims(String token)
     {
         return Jwts.parser().setSigningKey(SECRET_KEY)
         .parseClaimsJws(token).getBody();
     }

     private boolean isTokenExpired(String token)
     {
           return extractExpiration(token).before(new Date());
     }

     private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static String generateToken(UserDetails userdetails)
     {
         Map<String ,Object> claims  = new HashMap<>() ;
         return createToken(claims ,userdetails.getUsername()) ;
     }
  
    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims)
            .setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*10))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}

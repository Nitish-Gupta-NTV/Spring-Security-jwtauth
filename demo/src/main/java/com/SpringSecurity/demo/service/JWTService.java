package com.SpringSecurity.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private String secretekeys= "";
    public JWTService()
    {
        System.out.println("reach the jwt");
       try
       {
           KeyGenerator keys=KeyGenerator.getInstance("HmacSHA256");
           SecretKey sk= keys.generateKey();
            secretekeys=Base64.getEncoder().encodeToString(sk.getEncoded());
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
    }
    public String generateToken(String user)
    {
        System.out.println("generated token");
        Map<String,Object> token=new HashMap<>();
        return Jwts.builder().claims().add(token).subject(user)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+100*060*60*5))
                .and()
                .signWith(getkey())
                .compact();
    }

    private SecretKey getkey() {
        byte[]byteskey= Decoders.BASE64.decode(secretekeys);
        return Keys.hmacShaKeyFor(byteskey);
    }

    public String extractUsernamefromToken(String token) {
        return extractclaim(token,Claims::getSubject);
    }

    private <T> T extractclaim(String token, Function<Claims, T> claimResolver)
    {
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username=extractUsernamefromToken(token);

         return(username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractclaim(token, Claims::getExpiration);
    }
}

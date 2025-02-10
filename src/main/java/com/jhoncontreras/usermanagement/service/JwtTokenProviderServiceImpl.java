package com.jhoncontreras.usermanagement.service;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.jhoncontreras.usermanagement.utils.Constants;
import com.jhoncontreras.usermanagement.utils.DateTimeUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Implementation of token provider service
 */

@Service
public class JwtTokenProviderServiceImpl<T> implements JwtTokenProviderService<T> {

    public String generateToken(T identifier) {
        Date now = DateTimeUtils.convertToLocalDateTimeToDate(LocalDateTime.now());
        Date expiry = Constants.JWT_EXPIRATION > 0 ? DateTimeUtils.convertToLocalDateTimeToDate(LocalDateTime.now().plus(Duration.ofMillis(Constants.JWT_EXPIRATION))):null;
        Key key = Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes());
        
        return Jwts.builder()
                .setSubject(String.valueOf(identifier))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key)                	    
                .compact();
    }
}
package com.sudreeshya.sms.security.service.impl;

import com.sudreeshya.sms.dto.JwtDTO;
import com.sudreeshya.sms.security.service.JWTService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Service
public class JWTServiceImpl implements JWTService<JwtDTO> {

    @Override
    public String generateToken(JwtDTO source) {

        String secretKey = "2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgU";

        return Jwts
                .builder()
                .setClaims(source.getClaims())
                .setSubject(source.getEmailAddress())
                .setExpiration(source.getExpiryAt())
                .setIssuedAt(source.getIssueAt())
                .setIssuer(source.getIssuer())
                .setId(source.getId().toString())
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    @Override
    public Boolean verifyToken(String token) {
        return null;
    }
}

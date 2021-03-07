package com.sudreeshya.sms.security.service.impl;

import com.sudreeshya.sms.dto.JwtDTO;
import com.sudreeshya.sms.security.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
@Service
public class JWTServiceImpl implements JWTService<JwtDTO> {
    public String secretKey = "2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgU";

    @Override
    public String generateToken(JwtDTO source) {
        String base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .builder()
                .setClaims(source.getClaims())
                .setSubject(source.getEmailAddress())
                .setExpiration(source.getExpiryAt())
                .setIssuedAt(source.getIssueAt())
                .setIssuer(source.getIssuer())
                .setId(source.getId().toString())
                // signWith accepts Base64 encoded secret key
                .signWith(SignatureAlgorithm.HS512, base64EncodedSecretKey)
                .compact();
    }

    @Override
    public JwtDTO verifyToken(String token) {
        final JwtDTO.JwtDTOBuilder jwtDTOBuilder = JwtDTO.builder();
        //This line will throw an exception if it is not a signed JWS (as expected)
        try {
            final Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);

            final Claims body = claimsJws.getBody();

            final Date expiration = body.getExpiration();

            log.debug("Expires in: {}", expiration);

            return jwtDTOBuilder
                    .authenticated(true)
                    .claims(body)
                    .emailAddress(body.getSubject())
                    .issueAt(body.getIssuedAt())
                    .issuer(body.getIssuer())
                    .expiryAt(body.getExpiration())
                    .id(Long.valueOf(body.getId()))
                    .build();

        } catch (Exception ex) {
            log.debug("Exception verifying the token: {}", ex.getMessage());
        }

        return jwtDTOBuilder.build();
    }
}

package com.sudreeshya.sms.security.service.impl;

import com.sudreeshya.sms.dto.JwtDTO;
import com.sudreeshya.sms.security.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public Optional<JwtDTO> verifyToken(String token) {
        JwtDTO jwtDTO = null;
        //This line will throw an exception if it is not a signed JWS (as expected)
        try {
            final Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            final Claims body = claimsJws.getBody();

            final Date expiration = body.getExpiration();

            log.debug("Expires in: {}", expiration);

            jwtDTO = JwtDTO
                    .builder()
                    .claims(null)
                    .emailAddress(body.getSubject())
                    .issueAt(body.getIssuedAt())
                    .issuer(body.getIssuer())
                    .expiryAt(body.getExpiration())
                    .id(Long.valueOf(body.getId()))
                    .build();

            log.debug("Token data :{}", jwtDTO);

        } catch (Exception ex) {
            log.debug("Exception verifying the token: {}", ex.getMessage());
        }

        return Optional.of(jwtDTO);
    }
}

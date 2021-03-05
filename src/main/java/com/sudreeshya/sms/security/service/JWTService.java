package com.sudreeshya.sms.security.service;

import java.util.Optional;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface JWTService<I> {

    String generateToken(I data);

    Optional<I> verifyToken(String token);
}

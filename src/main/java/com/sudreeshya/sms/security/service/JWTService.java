package com.sudreeshya.sms.security.service;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface JWTService<I> {

    String generateToken(I data);

    Boolean verifyToken(String token);
}

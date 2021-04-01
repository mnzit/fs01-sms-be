package com.sudreeshya.sms.security.service;

import com.sudreeshya.sms.provider.InvalidJwtException;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface JWTService<I> {

    String generateToken(I data);

    I verifyToken(String token) throws InvalidJwtException;
}

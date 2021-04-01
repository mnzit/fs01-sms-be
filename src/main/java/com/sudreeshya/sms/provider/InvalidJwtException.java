package com.sudreeshya.sms.provider;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class InvalidJwtException extends AuthenticationException {
    public InvalidJwtException(String msg) {
        super(msg);
    }
}

package com.sudreeshya.sms.provider;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class IncorrectCredentialException extends AuthenticationException {
    public IncorrectCredentialException(String msg) {
        super(msg);
    }
}

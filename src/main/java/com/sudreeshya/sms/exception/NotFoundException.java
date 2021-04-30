package com.sudreeshya.sms.exception;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}

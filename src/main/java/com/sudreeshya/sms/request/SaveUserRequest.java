package com.sudreeshya.sms.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Getter
@Setter
public class SaveUserRequest implements Serializable {

    private String firstname;
    private String lastname;
    private String address;
    private String contactNo;
    private String emailAddress;
    private String password;
}

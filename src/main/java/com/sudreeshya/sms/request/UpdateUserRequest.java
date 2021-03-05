package com.sudreeshya.sms.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Getter
@Setter
@ToString
public class UpdateUserRequest implements Serializable {

    private String firstName;
    private String lastName;
    private String address;
    private String contactNo;
    private String emailAddress;
    private String password;
}

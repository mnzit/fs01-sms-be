package com.sudreeshya.sms.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Getter
@Setter
public class UserDTO implements Serializable {
    private Long id;
    private String firstname;
    private String lastname;
    private String address;
    private String contactNo;
    private String emailAddress;
}

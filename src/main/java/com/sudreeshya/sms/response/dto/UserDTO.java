package com.sudreeshya.sms.response.dto;

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
public class UserDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String contactNo;
    private String emailAddress;
    private Character isActive;
}

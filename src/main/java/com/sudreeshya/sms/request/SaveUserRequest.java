package com.sudreeshya.sms.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */

@Getter
@Setter
@ToString
public class SaveUserRequest implements Serializable {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String address;
    @NotEmpty
    private String contactNo;
    @NotEmpty
    private String emailAddress;
    @NotEmpty
    private String password;
    @NotEmpty
    private Long role;
}

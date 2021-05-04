package com.sudreeshya.sms.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contactNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String emailAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Character isActive;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long role;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long course;
}

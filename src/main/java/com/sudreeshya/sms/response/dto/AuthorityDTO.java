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
public class AuthorityDTO implements Serializable {
    private Long id;
    private String name;
}

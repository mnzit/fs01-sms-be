package com.sudreeshya.sms.response.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthSuccessResponse implements Serializable {

    @JsonIgnore
    private String token;
}

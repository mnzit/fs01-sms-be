package com.sudreeshya.sms.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Getter
@Builder
public class JwtDTO {

    private Map<String, Object> claims;
    private Date issueAt;
    private String issuer = "SMS";
    private Long id;
    private Date expiryAt;
    private String emailAddress;
}

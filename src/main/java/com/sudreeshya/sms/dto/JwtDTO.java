package com.sudreeshya.sms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Getter
@Builder
public class JwtDTO {
    private boolean authenticated;
    private Map<String, Object> claims;
    private Date issueAt;
    private String issuer = "SMS";
    private Long id;
    private Date expiryAt;
    private String emailAddress;

    @Override
    public String toString() {
        return "JwtDTO{" +
                "claims=" + claims +
                ", issueAt=" + issueAt +
                ", issuer='" + issuer + '\'' +
                ", id=" + id +
                ", expiryAt=" + expiryAt +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}

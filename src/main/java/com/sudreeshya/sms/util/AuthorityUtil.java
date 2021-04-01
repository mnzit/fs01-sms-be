package com.sudreeshya.sms.util;

import com.sudreeshya.sms.model.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class AuthorityUtil {

    public static Collection<? extends GrantedAuthority> buildAuthorities(List<Authority> authorities) {
        return authorities
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }
}

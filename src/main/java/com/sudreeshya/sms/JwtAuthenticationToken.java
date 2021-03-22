package com.sudreeshya.sms;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 540L;
    private final Object principal;
    private Object credentials;
    @Getter
    private String token;

    public JwtAuthenticationToken(String token) {
        super((Collection) null);
        this.principal = null;
        this.credentials = null;
        this.token = token;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}

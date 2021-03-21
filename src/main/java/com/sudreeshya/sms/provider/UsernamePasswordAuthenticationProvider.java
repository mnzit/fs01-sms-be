package com.sudreeshya.sms.provider;

import com.sudreeshya.sms.dto.CustomUserDetail;
import com.sudreeshya.sms.model.ApplicationUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final CustomUserDetail customUserDetail = (CustomUserDetail) userDetails;
        final ApplicationUser applicationUser = customUserDetail.getApplicationUser();
        final Authentication afterAuthenticated = new UsernamePasswordAuthenticationToken(applicationUser, applicationUser.getPassword(), null);

        if(!validatePassword(password, applicationUser)){
            throw new IncorrectCredentialException("Username/Password is not correct");
        }

        SecurityContextHolder.getContext().setAuthentication(afterAuthenticated);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    public Boolean validatePassword(String requestedPassword, ApplicationUser applicationUser){
        return requestedPassword.equals(applicationUser.getPassword());
    }
}

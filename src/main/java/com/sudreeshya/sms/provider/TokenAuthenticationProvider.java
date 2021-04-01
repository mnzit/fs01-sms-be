package com.sudreeshya.sms.provider;

import com.sudreeshya.sms.JwtAuthenticationToken;
import com.sudreeshya.sms.dto.CustomUserDetail;
import com.sudreeshya.sms.dto.JwtDTO;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.security.service.JWTService;
import com.sudreeshya.sms.util.AuthorityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
public class TokenAuthenticationProvider implements AuthenticationProvider {


    private UserDetailsService userDetailsService;

    @Autowired
    private JWTService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        final String token = jwtAuthenticationToken.getToken();
        final JwtDTO jwtData = (JwtDTO) jwtService.verifyToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtData.getEmailAddress());
        final CustomUserDetail customUserDetail = (CustomUserDetail) userDetails;
        final ApplicationUser applicationUser = customUserDetail.getApplicationUser();
        authentication =
                new JwtAuthenticationToken(
                        applicationUser,
                        applicationUser.getPassword(),
                        AuthorityUtil
                                .buildAuthorities(applicationUser.getAuthorities())
                );

        // updating the thread local with the currently logged in user
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> token) {
        return token.equals(JwtAuthenticationToken.class);
    }

    public void setUserDetails(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}

package com.sudreeshya.sms.security.service.impl;

import com.sudreeshya.sms.dto.CustomUserDetail;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class JdbcUserDetailService implements UserDetailsService {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final ApplicationUser applicationUser = applicationUserRepository
                .findByEmailAddress(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetail(applicationUser);
    }
}

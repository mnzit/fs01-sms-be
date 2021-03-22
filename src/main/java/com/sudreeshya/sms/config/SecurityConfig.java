package com.sudreeshya.sms.config;

import com.sudreeshya.sms.filter.AuthenticationErrorEntry;
import com.sudreeshya.sms.filter.JWTAuthFilter;
import com.sudreeshya.sms.provider.TokenAuthenticationProvider;
import com.sudreeshya.sms.provider.UsernamePasswordAuthenticationProvider;
import com.sudreeshya.sms.security.service.impl.JdbcUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // handle an authorized attempts
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                // any other requests must be authenticated
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        final TokenAuthenticationProvider tokenAuthenticationProvider = new TokenAuthenticationProvider();
        tokenAuthenticationProvider.setUserDetails(userDetailsService());
        auth.authenticationProvider(tokenAuthenticationProvider);

        auth.authenticationProvider(new UsernamePasswordAuthenticationProvider());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationErrorEntry();
    }

    @Bean
    public JWTAuthFilter jwtAuthFilter() throws Exception {
        JWTAuthFilter jwtAuthFilter = new JWTAuthFilter(new NegatedRequestMatcher(new AntPathRequestMatcher("/login")));
        jwtAuthFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new JdbcUserDetailService();
    }
}

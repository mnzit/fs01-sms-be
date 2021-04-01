package com.sudreeshya.sms.config;

import com.sudreeshya.sms.auth.url.SecurePath;
import com.sudreeshya.sms.filter.AuthenticationErrorEntry;
import com.sudreeshya.sms.filter.JwtAuthenticationFailureHandler;
import com.sudreeshya.sms.filter.JWTAuthFilter;
import com.sudreeshya.sms.provider.TokenAuthenticationProvider;
import com.sudreeshya.sms.provider.UsernamePasswordAuthenticationProvider;
import com.sudreeshya.sms.security.service.impl.JdbcUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Custom JWT based security filter
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .headers()
                .xssProtection()
                .and()
                .frameOptions()
                .disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenBasedProvider());
        auth.authenticationProvider(usernamePasswordBasedProvider());
    }

    @Bean
    public AuthenticationProvider tokenBasedProvider() {
        final TokenAuthenticationProvider tokenAuthenticationProvider = new TokenAuthenticationProvider();
        tokenAuthenticationProvider.setUserDetails(userDetailsService());
        return tokenAuthenticationProvider;
    }

    @Bean
    public AuthenticationProvider usernamePasswordBasedProvider() {
        final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider = new UsernamePasswordAuthenticationProvider();
        usernamePasswordAuthenticationProvider.setUserDetails(userDetailsService());
        return usernamePasswordAuthenticationProvider;
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
        JWTAuthFilter jwtAuthFilter = new JWTAuthFilter(SecurePath.JWT_URLS);
        jwtAuthFilter.setAuthenticationManager(authenticationManager());
        jwtAuthFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        jwtAuthFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return jwtAuthFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new JdbcUserDetailService();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new JwtAuthenticationFailureHandler();
    }
}

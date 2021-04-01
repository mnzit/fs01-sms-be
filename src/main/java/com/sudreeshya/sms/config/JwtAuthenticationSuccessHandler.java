package com.sudreeshya.sms.config;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Defines where to go after successful login. In this implementation just make sure nothing is done (REST API contains no pages)
 *
 */
@Slf4j
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Do do anything specific here
    log.debug("Success in JWT auth");
    }

}

package com.sudreeshya.sms.filter;

import com.sudreeshya.sms.JwtAuthenticationToken;
import com.sudreeshya.sms.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
public class JWTAuthFilter extends AbstractAuthenticationProcessingFilter {

    public JWTAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.debug("Reached Authorization filter");
        String token = request.getHeader(SecurityConstant.JWT_TOKEN_KEY); // key

        if (token == null) {
            throw new AuthenticationServiceException("Authorization token not found");
        }

        if (!token.contains(SecurityConstant.JWT_TOKEN_PREFIX)) {
            throw new AuthenticationServiceException("Authorization token not found");
        }

        token = token.replace(SecurityConstant.JWT_TOKEN_PREFIX, "");

        if (!(token.trim().length() > 0)) {
            throw new AuthenticationServiceException("Authorization token not found");
        }
        final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
        Authentication authentication = getAuthenticationManager()
                .authenticate(jwtAuthenticationToken);
        DaoAuthenticationProvider
        this.setDetails(request, jwtAuthenticationToken);

        return authentication;
    }

    @Override
    protected AuthenticationFailureHandler getFailureHandler() {
        return super.getFailureHandler();
    }

    protected void setDetails(HttpServletRequest request, JwtAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}

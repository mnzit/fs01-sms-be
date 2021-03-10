package com.sudreeshya.sms.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.SecurityConstant;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.dto.JwtDTO;
import com.sudreeshya.sms.security.service.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Component
@Slf4j
@AllArgsConstructor
public class JWTAuthFilter implements Filter {

    private final JWTService<JwtDTO> jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        log.debug("Reached Authorization filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().contains("login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(SecurityConstant.JWT_TOKEN_KEY); // key

        if (token == null) {
            buildFailure(response, "Authorization token not found");
            return;
        }

        if (!token.contains(SecurityConstant.JWT_TOKEN_PREFIX)) {
            buildFailure(response, "Authorization token not found");
            return;
        }

        token = token.replace(SecurityConstant.JWT_TOKEN_PREFIX, "");

        if (!(token.trim().length() > 0)) {
            buildFailure(response, "Authorization token not found");
            return;
        }

        log.debug("Jwt token : {}", token);
        final JwtDTO jwtData = jwtService.verifyToken(token);
        log.debug("isAuth: {}", jwtData.isAuthenticated());

        if (!jwtData.isAuthenticated()) {
            buildFailure(response, "Unauthorized");
            return;
        } else {
            // go to the next filter in the filter chain
            filterChain.doFilter(request, response);
        }


    }

    public void buildFailure(HttpServletResponse response, String msg) throws IOException {
        final GenericResponse genericResponse = ResponseBuilder.buildFailure(msg);
        String responseString = new ObjectMapper().writeValueAsString(genericResponse);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getOutputStream().write(responseString.getBytes());
    }

}

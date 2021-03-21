package com.sudreeshya.sms.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.SecurityConstant;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.dto.JwtDTO;
import com.sudreeshya.sms.model.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
@AllArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("Reached Authorization filter");

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

    }

    public void buildFailure(HttpServletResponse response, String msg) throws IOException {
        final GenericResponse genericResponse = ResponseBuilder.buildFailure(msg);
        String responseString = new ObjectMapper().writeValueAsString(genericResponse);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getOutputStream().write(responseString.getBytes());
    }

}

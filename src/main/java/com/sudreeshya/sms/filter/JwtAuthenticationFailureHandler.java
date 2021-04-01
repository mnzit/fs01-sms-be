package com.sudreeshya.sms.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.dto.GenericResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        final GenericResponse genericResponse = ResponseBuilder.buildFailure(exception.getMessage());
        String responseString = new ObjectMapper().writeValueAsString(genericResponse);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getOutputStream().write(responseString.getBytes());
    }

}

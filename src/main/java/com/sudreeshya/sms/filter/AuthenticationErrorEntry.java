package com.sudreeshya.sms.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.dto.GenericResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class AuthenticationErrorEntry implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        final GenericResponse genericResponse = ResponseBuilder.buildFailure(e.getMessage());
        String responseString = new ObjectMapper().writeValueAsString(genericResponse);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getOutputStream().write(responseString.getBytes());
    }
}

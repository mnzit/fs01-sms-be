package com.sudreeshya.sms.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudreeshya.sms.builder.ResponseBuilder;
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
import java.util.Optional;

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
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().contains("login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization"); // key
        log.debug("Jwt token : {}", token);
        final Optional<JwtDTO> jwtData = jwtService.verifyToken(token);

        if (!jwtData.isPresent()) {
            final GenericResponse genericResponse = ResponseBuilder.buildFailure("Unauthorized");
            String responseString = new ObjectMapper().writeValueAsString(genericResponse);
            response.setContentType("application/json");
            response.setStatus(401);
            response.getOutputStream().write(responseString.getBytes());
        }

        // go to the next filter in the filter chain
        filterChain.doFilter(request, response);
    }
}

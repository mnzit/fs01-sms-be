package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.dto.JwtDTO;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.model.Authority;
import com.sudreeshya.sms.repository.ApplicationUserRepository;
import com.sudreeshya.sms.request.AuthRequest;
import com.sudreeshya.sms.response.dto.AuthSuccessResponse;
import com.sudreeshya.sms.security.service.JWTService;
import com.sudreeshya.sms.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ApplicationUserRepository applicationUserRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public GenericResponse login(AuthRequest request) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmailAddress(), request.getPassword()));

        log.info("authRequest: {}", request);
        Optional<ApplicationUser> user = applicationUserRepository.findByEmailAddress(request.getEmailAddress());
        log.info("user: {}", user);
        if (user.isPresent()) {
            ApplicationUser applicationUser = user.get();
            log.info("applicationUser: {}", applicationUser);
            if (applicationUser.getPassword().equalsIgnoreCase(request.getPassword())) {

                Long currentTime = System.currentTimeMillis();
                Long expiryInSeconds = 300L;

                Map<String, Object> claims = new HashMap<>();
                String authorities = applicationUser
                        .getAuthorities()
                        .stream()
                        .map(Authority::getName)
                        .collect(Collectors.joining(","));

                claims.put("authorities", authorities);

                final JwtDTO jwtData = JwtDTO
                        .builder()
                        .claims(claims)
                        .emailAddress(applicationUser.getEmailAddress())
                        .issueAt(new Date(currentTime))
                        .expiryAt(new Date(currentTime + expiryInSeconds * 1000))
                        .id(applicationUser.getId())
                        .build();


                String token = jwtService.generateToken(jwtData);
                return ResponseBuilder.buildSuccess("Login successful", new AuthSuccessResponse(token));

            } else {
                return ResponseBuilder.buildFailure("Username or Password is incorrect");
            }
        } else {
            return ResponseBuilder.buildFailure("Username or Password is incorrect");
        }
    }
}

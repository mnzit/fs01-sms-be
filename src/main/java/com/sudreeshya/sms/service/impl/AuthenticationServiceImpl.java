package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.dto.JwtDTO;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.repository.ApplicationUserRepository;
import com.sudreeshya.sms.request.AuthRequest;
import com.sudreeshya.sms.response.dto.AuthSuccessResponse;
import com.sudreeshya.sms.security.service.JWTService;
import com.sudreeshya.sms.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ApplicationUserRepository applicationUserRepository;
    private final JWTService jwtService;

    @Autowired
    public AuthenticationServiceImpl(ApplicationUserRepository applicationUserRepository,
                                     JWTService jwtService) {
        this.applicationUserRepository = applicationUserRepository;
        this.jwtService = jwtService;
    }

    @Override
    public GenericResponse login(AuthRequest request) {
        Optional<ApplicationUser> user = applicationUserRepository.findByEmailAddress(request.getEmail());
        if (user.isPresent()) {
            ApplicationUser applicationUser = user.get();
            if (applicationUser.getPassword().equalsIgnoreCase(request.getPassword())) {

                Long currentTime = System.currentTimeMillis();
                Long expiryInSeconds = 300L;

                Map<String, Object> role = new HashMap<>();

                role.put("role","user");
                final JwtDTO jwtData = JwtDTO
                        .builder()
                        .claims(role)
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

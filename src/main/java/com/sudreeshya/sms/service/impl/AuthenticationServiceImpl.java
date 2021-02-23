package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.repository.ApplicationUserRepository;
import com.sudreeshya.sms.request.AuthRequest;
import com.sudreeshya.sms.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ApplicationUserRepository applicationUserRepository;

    public AuthenticationServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public GenericResponse login(AuthRequest request) {
        Optional<ApplicationUser> user = applicationUserRepository.findByEmailAddress(request.getEmail());
        if (user.isPresent()) {
            ApplicationUser applicationUser = user.get();
            if (applicationUser.getPassword().equalsIgnoreCase(request.getPassword())) {
                // TODO: add JWT token in header
                return ResponseBuilder.buildSuccess("Login successful");

            } else {
                return ResponseBuilder.buildFailure("Username or Password is incorrect");
            }
        } else {
            return ResponseBuilder.buildFailure("Username or Password is incorrect");
        }
    }
}

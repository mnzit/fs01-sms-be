package com.sudreeshya.sms.controller;

import com.sudreeshya.sms.constant.ApiPathConstants;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.AuthRequest;
import com.sudreeshya.sms.request.UpdateUserRequest;
import com.sudreeshya.sms.service.ApplicationUserService;
import com.sudreeshya.sms.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
@RestController
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = ApiPathConstants.LOGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> login(@RequestBody AuthRequest request) {
        log.debug("User authentication triggered...");
        GenericResponse response = authenticationService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package com.sudreeshya.sms.controller;

import com.sudreeshya.sms.constant.SecurityConstant;
import com.sudreeshya.sms.constant.APIPathConstants;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.AuthRequest;
import com.sudreeshya.sms.response.dto.AuthSuccessResponse;
import com.sudreeshya.sms.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = APIPathConstants.LOGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> login(@RequestBody AuthRequest request) {
        log.debug("User authentication triggered...");
        GenericResponse genericResponse = authenticationService.login(request);
        AuthSuccessResponse response = (AuthSuccessResponse) genericResponse.getData();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstant.JWT_TOKEN_KEY, SecurityConstant.JWT_TOKEN_PREFIX + response.getToken());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(genericResponse);
    }
}

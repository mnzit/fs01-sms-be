package com.sudreeshya.sms.controller;

import com.sudreeshya.sms.constant.ApiPathConstants;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.SaveUserRequest;
import com.sudreeshya.sms.request.UpdateUserRequest;
import com.sudreeshya.sms.service.ApplicationUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
@RestController
@RequestMapping(ApiPathConstants.USERS)
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;

    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllUsers() {
        log.debug("User find all triggered...");
        GenericResponse genericResponse = applicationUserService.getAllApplicationUser();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = ApiPathConstants.PathVariable.USERID_WRAPPER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findUserById(@PathVariable(ApiPathConstants.PathVariable.USERID) Long id) {
        log.debug("User find by id triggered...");
        GenericResponse genericResponse = applicationUserService.getApplicationUserById(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }


    @PostMapping(value = ApiPathConstants.SharedOperations.SAVE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> saveApplicationUser(@RequestBody SaveUserRequest request) {
        log.debug("User save triggered...");
        GenericResponse genericResponse = applicationUserService.saveApplicationUser(request);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }


    @PostMapping(value = ApiPathConstants.SharedOperations.UPDATE + "/" + ApiPathConstants.PathVariable.USERID_WRAPPER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> updateApplicationUser(@PathVariable(ApiPathConstants.PathVariable.USERID) Long id, @RequestBody UpdateUserRequest request) {
        log.debug("User update triggered...");
        GenericResponse genericResponse = applicationUserService.updateApplicationUser(id, request);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

}

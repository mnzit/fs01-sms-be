package com.sudreeshya.sms.controller;

import com.sudreeshya.sms.aop.annotation.MethodLogger;
import com.sudreeshya.sms.constant.APIPathConstants;
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
@RequestMapping(APIPathConstants.USERS)
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;

    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @MethodLogger
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllUsers() {
        GenericResponse genericResponse = applicationUserService.getAllApplicationUser();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @MethodLogger
    @GetMapping(value = "test",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        return "Test Success";
    }

    @MethodLogger
    @GetMapping(value = APIPathConstants.PathVariable.USERID_WRAPPER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findUserById(@PathVariable(APIPathConstants.PathVariable.USERID) Long id) {
        GenericResponse genericResponse = applicationUserService.getApplicationUserById(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @MethodLogger
    @PostMapping(value = APIPathConstants.SharedOperations.SAVE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> saveApplicationUser(@RequestBody SaveUserRequest request) {
        GenericResponse genericResponse = applicationUserService.saveApplicationUser(request);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }


    @PostMapping(value = APIPathConstants.SharedOperations.UPDATE + "/" + APIPathConstants.PathVariable.USERID_WRAPPER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> updateApplicationUser(@PathVariable(APIPathConstants.PathVariable.USERID) Long id, @RequestBody UpdateUserRequest request) {
        log.debug("User update triggered...");
        GenericResponse genericResponse = applicationUserService.updateApplicationUser(id, request);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.SharedOperations.DELETE + "/" + APIPathConstants.PathVariable.USERID_WRAPPER,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> deleteApplicationUser
            (@PathVariable(APIPathConstants.PathVariable.USERID) Long id) {
        GenericResponse genericResponse = applicationUserService.deleteApplicationUser(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.SharedOperations.TRASH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findDeletedUsers() {
        GenericResponse genericResponse = applicationUserService.findDeletedUsers();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

}

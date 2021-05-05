package com.sudreeshya.sms.controller;

import com.sudreeshya.sms.constant.APIPathConstants;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.SaveUserRequest;
import com.sudreeshya.sms.request.UpdateUserRequest;
import com.sudreeshya.sms.service.ApplicationUserService;
import com.sudreeshya.sms.util.OutputStreamUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
@RestController
@RequestMapping(APIPathConstants.USERS)
@AllArgsConstructor
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllUsers() {
        GenericResponse genericResponse = applicationUserService.getAllApplicationUser();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.PathVariable.USERID_WRAPPER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findUserById(@PathVariable(APIPathConstants.PathVariable.USERID) Long id) {
        GenericResponse genericResponse = applicationUserService.getApplicationUserById(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @SneakyThrows
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

    @GetMapping(value = APIPathConstants.SharedOperations.DOWNLOAD)
    public ResponseEntity<?> downloadApplicationUser(HttpServletResponse httpServletResponse) {

        String fileName = "users.xls";

        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        httpServletResponse.setContentType("application/vnd.ms-excel");
        final ByteArrayOutputStream download = applicationUserService.download();
        OutputStreamUtil.writeToHttpServletResponse(httpServletResponse, download);
        return ResponseEntity.ok().build();
    }

}

package com.sudreeshya.sms.controller;

import com.sudreeshya.sms.constant.APIPathConstants;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.service.AttendanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
@RestController
@RequestMapping(APIPathConstants.ATTENDANCE)
@AllArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping(value = APIPathConstants.USERS + "/" + APIPathConstants.PathVariable.USERID_WRAPPER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findByApplicationUserId(@PathVariable(APIPathConstants.PathVariable.USERID) Long id) {
        GenericResponse genericResponse = attendanceService.findByApplicationUserId(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }


    @GetMapping(value = APIPathConstants.COURSES + "/" + APIPathConstants.PathVariable.COURSEID_WRAPPER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllByCourseId(@PathVariable(APIPathConstants.PathVariable.COURSEID) Long id) {
        GenericResponse genericResponse = attendanceService.findAllCourseId(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = APIPathConstants.SharedOperations.SAVE + "/" + APIPathConstants.PathVariable.USERID_WRAPPER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> saveAttendance(@PathVariable(APIPathConstants.PathVariable.USERID) Long id) {
        GenericResponse genericResponse = attendanceService.saveAttendance(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}


package com.sudreeshya.sms.controller;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.ApiPathConstants;
import com.sudreeshya.sms.constant.ApiPathConstants;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.SaveCourseRequest;
import com.sudreeshya.sms.request.UpdateCourseRequest;
import com.sudreeshya.sms.service.impl.CourseServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping(ApiPathConstants.COURSES)
public class CourseController {
    private final CourseServiceImpl courseServiceImpl;

    public CourseController(CourseServiceImpl courseServiceImpl) {
        this.courseServiceImpl = courseServiceImpl;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllCourses() {
        GenericResponse genericResponse = courseServiceImpl.findAllCourses();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = ApiPathConstants.PathVariable.COURSEID_WRAPPER)
    public ResponseEntity<GenericResponse> findCourseById(@PathVariable(ApiPathConstants.PathVariable.COURSEID) long id) {
        GenericResponse genericResponse = courseServiceImpl.findCourseById(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = ApiPathConstants.SharedOperations.SAVE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> saveCourse(@RequestBody SaveCourseRequest request){
        GenericResponse genericResponse = courseServiceImpl.saveCourse(request);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = ApiPathConstants.SharedOperations.UPDATE + "/" + ApiPathConstants.PathVariable.COURSEID_WRAPPER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> updateCourse(@PathVariable(ApiPathConstants.PathVariable.COURSEID) Long id,
                                                        @RequestBody UpdateCourseRequest request){
        GenericResponse genericResponse = courseServiceImpl.updateCourse(request, id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

}

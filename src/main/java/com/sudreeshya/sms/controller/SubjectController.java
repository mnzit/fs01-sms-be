package com.sudreeshya.sms.controller;

import com.sudreeshya.sms.constant.ApiPathConstants;

import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.SaveSubjectRequest;
import com.sudreeshya.sms.request.UpdateSubjectRequest;
import com.sudreeshya.sms.service.impl.SubjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(ApiPathConstants.SUBJECTS)
public class SubjectController {
    private final SubjectServiceImpl subjectServiceImpl;

    public SubjectController(SubjectServiceImpl subjectServiceImpl) {
        this.subjectServiceImpl = subjectServiceImpl;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllSubjects(){
        GenericResponse genericResponse = subjectServiceImpl.findAllSubjects();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = ApiPathConstants.PathVariable.SUBJECTID_WRAPPER)
    public ResponseEntity<GenericResponse> findSubjectById(@PathVariable(ApiPathConstants.PathVariable.SUBJECTID) long id){
        GenericResponse genericResponse = subjectServiceImpl.findSubjectById(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = ApiPathConstants.SharedOperations.SAVE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> saveSubject(@RequestBody SaveSubjectRequest request){
        GenericResponse genericResponse = subjectServiceImpl.saveSubject(request);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value =  ApiPathConstants.SharedOperations.UPDATE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> updateSubject(@PathVariable(ApiPathConstants.PathVariable.SUBJECTID) Long id,
                                                         @RequestBody UpdateSubjectRequest request){
        GenericResponse genericResponse = subjectServiceImpl.updateSubject(request, id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}



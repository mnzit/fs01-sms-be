package com.sudreeshya.sms.service;

import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.SaveCourseRequest;
import com.sudreeshya.sms.request.SaveSubjectRequest;
import com.sudreeshya.sms.request.UpdateCourseRequest;
import com.sudreeshya.sms.request.UpdateSubjectRequest;

public interface SubjectService {
    GenericResponse findAllSubjects();

    GenericResponse findSubjectById(Long id);

    GenericResponse saveSubject(SaveSubjectRequest request);

    GenericResponse updateSubject(UpdateSubjectRequest request, Long id);
}

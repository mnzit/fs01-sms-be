package com.sudreeshya.sms.service;

import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.SaveCourseRequest;
import com.sudreeshya.sms.request.UpdateCourseRequest;

public interface CourseService {
    GenericResponse findActiveCourses();

    GenericResponse findAllCourses();

    GenericResponse findCourseById(Long id);

    GenericResponse saveCourse(SaveCourseRequest request);

    GenericResponse updateCourse(UpdateCourseRequest request, Long id);

    GenericResponse deleteCourse(Long id);

    GenericResponse findDeletedCourses();

    GenericResponse rollBackDeletedCourse(Long id);
}

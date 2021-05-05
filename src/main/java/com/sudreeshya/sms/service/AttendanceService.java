package com.sudreeshya.sms.service;

import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.model.ApplicationUser;

public interface AttendanceService {

    GenericResponse findByApplicationUserId(Long id);

    GenericResponse findAllCourseId(Long id);

    GenericResponse saveAttendance(Long id);

}

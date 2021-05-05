package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.ResponseMsgConstant;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.model.Attendance;
import com.sudreeshya.sms.repository.ApplicationUserRepository;
import com.sudreeshya.sms.repository.AttendanceRepository;
import com.sudreeshya.sms.response.dto.AttendanceDTO;
import com.sudreeshya.sms.service.AttendanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public GenericResponse findByApplicationUserId(Long id) {
        List<Attendance> response = attendanceRepository.findAllByApplicationUserId(id);
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setCount(response.size());
        final List<AttendanceDTO.Report> report = response.stream().map(attendance -> new AttendanceDTO.Report(attendance.getCreatedDate())).collect(Collectors.toList());
        attendanceDTO.setReports(report);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.ATTENDANCE_SUCCESS, attendanceDTO);
    }

    @Override
    public GenericResponse findAllCourseId(Long id) {
        final List<Attendance> attendanceList = attendanceRepository.finAllByCourseId(id);
        Map<String, Long> response = attendanceList
                .stream()
                .collect(
                        Collectors.groupingBy(
                                (attendance) -> attendance.getApplicationUser().getEmailAddress(), Collectors.counting()));

        return ResponseBuilder.buildSuccess(ResponseMsgConstant.ATTENDANCE_SUCCESS, response);
    }

    @Override
    public GenericResponse saveAttendance(Long id) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        log.info("applicationUser: {}", applicationUserOptional);
        if (!applicationUserOptional.isPresent()) {
            return ResponseBuilder.buildFailure("Could not find the user.");
        }

        final ApplicationUser applicationUser = applicationUserOptional.get();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(date);

        Optional<Attendance> optionalAttendance = attendanceRepository.findByCreatedDate(currentDate, applicationUser.getId(), applicationUser.getCourse().getId());
        if (!optionalAttendance.isPresent()) {
            Attendance attendance = new Attendance();
            attendance.setCourse(applicationUser.getCourse());
            attendance.setApplicationUser(applicationUser);
            attendance.setCreatedBy(new ApplicationUser(1L));
            attendance.setCreatedDate(new Date());
            attendanceRepository.save(attendance);
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.ATTENDANCE_SAVED);
        } else {
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.ATTENDANCE_ALREADY_DONE);
        }
    }
}

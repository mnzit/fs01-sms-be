package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.ResponseMsgConstant;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.model.Course;
import com.sudreeshya.sms.repository.CourseRepository;
import com.sudreeshya.sms.request.SaveCourseRequest;
import com.sudreeshya.sms.request.UpdateCourseRequest;
import com.sudreeshya.sms.response.dto.CourseDTO;
import com.sudreeshya.sms.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public GenericResponse findAllCourses() {
        final List<Course> courses = courseRepository.findAll();
        if(courses.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_FOUND);
        }
        List<CourseDTO> courseDTOList = new ArrayList<>();

        courseDTOList = courses
                .stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());

        log.info("courseDTOList: {}", courseDTOList);

        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_FOUND, courseDTOList);

    }

    @Override
    public GenericResponse findCourseById(Long id) {
        final Optional<Course> courseOptional = courseRepository.findById(id);
        if(!courseOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_FOUND);
        }
        CourseDTO response = modelMapper.map(courseOptional.get(), CourseDTO.class);
        log.info("Response: {}", response);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_FOUND, response);
    }

    @Override
    public GenericResponse saveCourse(SaveCourseRequest request) {
        Course course = modelMapper.map(request, Course.class);
        course.setCreatedBy(new ApplicationUser(1L));
        log.info("course: {}", course);
        courseRepository.save(course);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_SAVED);
    }

    @Override
    public GenericResponse updateCourse(UpdateCourseRequest request, Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if(!courseOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_FOUND);
        }
        Course course = new Course();
        course = modelMapper.map(request, Course.class);
        course.setId(id);
        course.setCreatedBy(new ApplicationUser(1L));
        log.info("course: {}", course);
        courseRepository.save(course);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_UPDATED);

    }
}

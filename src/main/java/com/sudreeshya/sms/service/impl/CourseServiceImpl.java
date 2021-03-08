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
    public GenericResponse findActiveCourses(){
        final List<Course> allCourses = courseRepository.findAll();
        if(allCourses.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_FOUND);
        }
        List<CourseDTO> courseDTOList = new ArrayList<>();
        courseDTOList = allCourses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
        List<CourseDTO> activeCourses = new ArrayList<>();
        for(CourseDTO c : courseDTOList){
            if(c.getIsActive() == 'Y'){
                activeCourses.add(c);
            }
        }
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_FOUND, activeCourses);
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

        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_FOUND, courseDTOList);

    }

    @Override
    public GenericResponse findCourseById(Long id) {
        final Optional<Course> courseOptional = courseRepository.findById(id);
        if(!courseOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_FOUND);
        }
        if(courseOptional.get().getIsActive() == 'N'){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_WAS_DELETED);
        }
        CourseDTO response = modelMapper.map(courseOptional.get(), CourseDTO.class);
        log.info("Response: {}", response);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_FOUND, response);
    }

    @Override
    public GenericResponse saveCourse(SaveCourseRequest request) {
        log.info("request: {}", request);
        Course course = modelMapper.map(request, Course.class);
        log.info("course: {}", course);
        if(course.getName().isEmpty() || course.getCode().isEmpty() || course.getDescription().isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_CANT_BE_EMPTY);
        }
        course.setCreatedBy(new ApplicationUser(1L));
        course.setIsActive('Y');
        log.info("course: {}", course);
        log.info(course.getCreatedBy().toString());
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
        if(course.getName().isEmpty() || course.getCode().isEmpty() || course.getDescription().isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_CANT_BE_EMPTY);
        }
        course.setId(id);
        course.setCreatedBy(new ApplicationUser(1L));
        course.setIsActive('Y');
        log.info("course: {}", course);
        courseRepository.save(course);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_UPDATED);

    }

    @Override
    public GenericResponse deleteCourse(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if(!courseOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_FOUND);
        }
        else{
            Course course = new Course();
            course = modelMapper.map(courseOptional.get(), Course.class);
            course.setIsActive('N');
            courseRepository.save(course);
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_WAS_DELETED);
        }
    }

    @Override
    public GenericResponse findDeletedCourses() {
        final List<Course> courseOptional = courseRepository.findAll();
        if(courseOptional.isEmpty()){
            ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_FOUND);
        }
        List<CourseDTO> courseDTOList = new ArrayList<>();
        courseDTOList = courseOptional.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
        List<CourseDTO> courseTrash = new ArrayList<>();
        for(CourseDTO c : courseDTOList){
            if(c.getIsActive() == 'N'){
                courseTrash.add(c);
            }
        }
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_FOUND, courseTrash);
    }

    @Override
    public GenericResponse rollBackDeletedCourse(Long id){
        Optional<Course> courseOptional = courseRepository.findById(id);
        if(!courseOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_FOUND);
        }
        if(courseOptional.get().getIsActive() == 'Y'){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_IN_TRASH);
        }
        Course deletedCourse = new Course();
        deletedCourse = modelMapper.map(courseOptional.get(), Course.class);
        deletedCourse.setId(id);
        deletedCourse.setIsActive('Y');
        courseRepository.save(deletedCourse);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_ROLLEDBACK);
    }

    @Override
    public GenericResponse rollBackAllCourses(){
        List<Course> courseList = courseRepository.findAll();
        if(courseList.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_FOUND);
        }
        List<Course> courseTrash = new ArrayList<>();
        for(Course c : courseList){
            if(c.getIsActive() == 'N'){
                courseTrash.add(c);
            }
        }
        if(courseTrash.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.COURSE_NOT_IN_TRASH);
        }
        for(Course c : courseTrash){
            c.setIsActive('Y');
        }
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.ALL_COURSES_ROLLEDBACK);
    }
}

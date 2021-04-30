package com.sudreeshya.sms.builder;

import com.sudreeshya.sms.exception.NotFoundException;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.model.Course;
import com.sudreeshya.sms.repository.CourseRepository;
import com.sudreeshya.sms.request.SaveUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
public class ApplicationUserBuilder {

    public static ApplicationUser buildForSave(ModelMapper modelMapper, SaveUserRequest request, PasswordEncoder passwordEncoder, CourseRepository courseRepository) {
        ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);
        applicationUser.setCreatedBy(new ApplicationUser(1L));
        applicationUser.setIsActive('Y');
        applicationUser.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getCourse() != null) {
            final Optional<Course> course = courseRepository.findById(request.getCourse());
            applicationUser.setCourse(course.orElseThrow(() -> new NotFoundException("Course not found")));
        }
        return applicationUser;
    }
}

package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.async.event.EmailEvent;
import com.sudreeshya.sms.async.producer.EmailEventProducer;
import com.sudreeshya.sms.builder.ApplicationUserBuilder;
import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.ResponseMsgConstant;
import com.sudreeshya.sms.download.excel.service.ExcelService;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.exception.NotFoundException;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.model.ApplicationUserAuthority;
import com.sudreeshya.sms.model.Authority;
import com.sudreeshya.sms.model.Course;
import com.sudreeshya.sms.repository.ApplicationUserAuthorityRepository;
import com.sudreeshya.sms.repository.ApplicationUserRepository;
import com.sudreeshya.sms.repository.AuthorityRepository;
import com.sudreeshya.sms.repository.CourseRepository;
import com.sudreeshya.sms.request.SaveUserRequest;
import com.sudreeshya.sms.request.UpdateUserRequest;
import com.sudreeshya.sms.response.dto.UserDTO;
import com.sudreeshya.sms.service.ApplicationUserService;
import com.sudreeshya.sms.validator.ApplicationUserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
@Service
@AllArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserAuthorityRepository applicationUserAuthorityRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final EmailEventProducer emailEventProducer;
    private final CourseRepository courseRepository;
    private final ExcelService<List<UserDTO>,ByteArrayOutputStream> excelService;

    @Override
    public GenericResponse getAllApplicationUser() {

        final List<ApplicationUser> applicationUsers = applicationUserRepository.findAll();
        log.info("applicationUsers: {}", applicationUsers);

        if (applicationUsers.isEmpty()) {
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }

        List<UserDTO> userDTOList = new ArrayList<>();


        userDTOList = applicationUsers
                .stream()
                .map(applicationUser -> {
                    UserDTO userDTO = new UserDTO();

                    userDTO.setId(applicationUser.getId());
                    userDTO.setAddress(applicationUser.getAddress());
                    userDTO.setContactNo(applicationUser.getContactNo());
                    userDTO.setIsActive(applicationUser.getIsActive());
                    userDTO.setFirstName(applicationUser.getFirstName());
                    userDTO.setLastName(applicationUser.getLastName());
                    userDTO.setEmailAddress(applicationUser.getEmailAddress());
                    if (applicationUser.getAuthorities() != null && !applicationUser.getAuthorities().isEmpty() && applicationUser.getAuthorities().get(0) != null) {
                        userDTO.setRole(applicationUser.getAuthorities().get(0).getId());
                    }
                    if (applicationUser.getCourse() != null) {
                        userDTO.setCourse(applicationUser.getCourse().getId());
                    }

                    return userDTO;
                })
                .collect(Collectors.toList());

        log.info("userDTOList: {}", userDTOList);


        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, userDTOList);
    }

    @Override
    public GenericResponse getApplicationUserById(Long id) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        log.info("applicationUser: {}", applicationUserOptional);
        if (!applicationUserOptional.isPresent()) {
            return ResponseBuilder.buildFailure("Could not find the user.");
        } else {
            ApplicationUser applicationUser = applicationUserOptional.get();
            UserDTO response = new UserDTO();

            response.setId(applicationUser.getId());
            response.setAddress(applicationUser.getAddress());
            response.setContactNo(applicationUser.getContactNo());
            response.setIsActive(applicationUser.getIsActive());
            response.setFirstName(applicationUser.getFirstName());
            response.setLastName(applicationUser.getLastName());
            response.setEmailAddress(applicationUser.getEmailAddress());

            if (applicationUser.getAuthorities() != null && !applicationUser.getAuthorities().isEmpty() && applicationUser.getAuthorities().get(0) != null) {
                response.setRole(applicationUser.getAuthorities().get(0).getId());
            }

            if (applicationUser.getCourse() != null) {
                response.setCourse(applicationUser.getCourse().getId());
            }

            if (response.getIsActive() == 'N') {
                return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_WAS_DELETED);
            }

            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, response);
        }
    }

    @Transactional
    @Override
    public GenericResponse saveApplicationUser(SaveUserRequest request) {
        // Build from request
        ApplicationUser applicationUser = null;
        applicationUser = ApplicationUserBuilder.buildForSave(modelMapper, request, passwordEncoder, courseRepository);

        // Perform Validation
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        ApplicationUserValidator.validateForSave(applicationUserList, request.getEmailAddress());
        // Persist in DB
        applicationUser = applicationUserRepository.save(applicationUser);
        authorityRepository.saveAuthority(applicationUser.getId(), request.getRole());
        // Send Mail
        emailEventProducer.sendEmail(new EmailEvent(
                applicationUser.getEmailAddress(),
                "User created successfully",
                "email:  " + applicationUser.getEmailAddress() + ", password: " + request.getPassword()
        ));
        // Return Success
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_SAVED);
    }

    @Transactional
    @Override
    public GenericResponse updateApplicationUser(Long id, UpdateUserRequest request) {
        log.info("request: {}", request);
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        if (!applicationUserOptional.isPresent()) {
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        } else {
            ApplicationUser applicationUser = new ApplicationUser();

            applicationUser.setId(id);
            applicationUser.setAddress(request.getAddress());
            applicationUser.setContactNo(request.getContactNo());
            applicationUser.setIsActive(request.getIsActive());
            applicationUser.setFirstName(request.getFirstName());
            applicationUser.setLastName(request.getLastName());
            applicationUser.setEmailAddress(request.getEmailAddress());
            applicationUser.setPassword(applicationUserOptional.get().getPassword());

            final ApplicationUserAuthority applicationUserAuthority = applicationUserAuthorityRepository.findApplicationUserAuthoritiesByApplicationUser_Id(id);

            if (applicationUserAuthority != null) {
                final Optional<Authority> newAuthority = authorityRepository.findById(request.getRole());
                applicationUserAuthority.setAuthority(newAuthority.orElseThrow(() -> new NotFoundException("Role not found")));
                applicationUserAuthorityRepository.save(applicationUserAuthority);
            }

            if (request.getCourse() != null) {
                final Optional<Course> course = courseRepository.findById(request.getCourse());

                applicationUser.setCourse(course.orElseThrow(() -> new NotFoundException("Course not found")));
            }
            applicationUser.setCreatedBy(new ApplicationUser(1L));
            applicationUser.setIsActive(request.getIsActive());
            applicationUserRepository.save(applicationUser);

            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_UPDATED);
        }

    }

    @Override
    public GenericResponse deleteApplicationUser(Long id) {
        log.info("id: {}", id);
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        log.info("Optional: {}", applicationUserOptional);
        if (!applicationUserOptional.isPresent()) {
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        } else {
            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser = modelMapper.map(applicationUserOptional.get(), ApplicationUser.class);
            applicationUser.setIsActive('N');
            applicationUserRepository.save(applicationUser);
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_WAS_DELETED);
        }
    }

    @Override
    public GenericResponse findDeletedUsers() {
        final List<ApplicationUser> applicationUsers = applicationUserRepository.findAll();
        if (applicationUsers.isEmpty()) {
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }
        List<UserDTO> userDTOList = new ArrayList<>();

        userDTOList = applicationUsers
                .stream()
                .map(appUsers -> modelMapper.map(appUsers, UserDTO.class))
                .collect(Collectors.toList());

        log.info("userDTOList: {}", userDTOList);

        List<UserDTO> usersTrash = new ArrayList<>();

        for (UserDTO userDTO : userDTOList) {
            if (userDTO.getIsActive() == 'N') {
                usersTrash.add(userDTO);
            }
        }
        log.info("Trash: {}", usersTrash);

        if (usersTrash.isEmpty()) {
            return ResponseBuilder.buildFailure(ResponseMsgConstant.NO_TRASH);
        } else {
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, usersTrash);
        }

    }

    @Override
    public ByteArrayOutputStream download() {
        final List<ApplicationUser> applicationUsers = applicationUserRepository.findAll();

        final List<UserDTO> collect = applicationUsers.stream().map((applicationUser) -> {
            UserDTO response = new UserDTO();

            response.setId(applicationUser.getId());
            response.setContactNo(applicationUser.getContactNo());
            response.setFirstName(applicationUser.getFirstName());
            response.setLastName(applicationUser.getLastName());
            response.setEmailAddress(applicationUser.getEmailAddress());
            return response;
        }).collect(Collectors.toList());
        try {
          return excelService.convert(collect);

        } catch (Exception e) {
            log.error("Excel conversion failed");
        }
        return null;
    }
}


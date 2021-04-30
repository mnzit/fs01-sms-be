package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.async.event.EmailEvent;
import com.sudreeshya.sms.async.producer.EmailEventProducer;
import com.sudreeshya.sms.builder.ApplicationUserBuilder;
import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.ResponseMsgConstant;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.model.ApplicationUser;
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
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final EmailEventProducer emailEventProducer;
    private final CourseRepository courseRepository;

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
                .map(applicationUser -> modelMapper.map(applicationUser, UserDTO.class))
                .collect(Collectors.toList());

        log.info("userDTOList: {}", userDTOList);


        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, userDTOList);
    }

    @Override
    public GenericResponse getApplicationUserById(Long id) {
        Optional<ApplicationUser> applicationUser = applicationUserRepository.findById(id);
        log.info("applicationUser: {}", applicationUser);
        if (!applicationUser.isPresent()) {
            return ResponseBuilder.buildFailure("Could not find the user.");
        } else {
            UserDTO response = modelMapper.map(applicationUser.get(), UserDTO.class);
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

    @Override
    public GenericResponse updateApplicationUser(Long id, UpdateUserRequest request) {
        log.info("request: {}", request);
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        if (!applicationUserOptional.isPresent()) {
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        } else {
            ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);

            if (applicationUser.getFirstName().isEmpty() || applicationUser.getLastName().isEmpty() ||
                    applicationUser.getPassword().isEmpty()) {
                return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_CANT_BE_EMPTY);
            }

            applicationUser.setId(id);
            applicationUser.setCreatedBy(new ApplicationUser(1L));
            applicationUser.setIsActive('Y');
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

}


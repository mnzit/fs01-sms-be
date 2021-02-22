package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.ResponseMsgConstant;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.repository.ApplicationUserRepository;
import com.sudreeshya.sms.request.SaveUserRequest;
import com.sudreeshya.sms.request.UpdateUserRequest;
import com.sudreeshya.sms.response.dto.UserDTO;
import com.sudreeshya.sms.service.ApplicationUserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Slf4j
@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository, ModelMapper modelMapper) {
        this.applicationUserRepository = applicationUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenericResponse getAllApplicationUser() {
        final List<ApplicationUser> applicationUsers = applicationUserRepository.findAll();

        if (applicationUsers != null && applicationUsers.isEmpty()) {
            return ResponseBuilder.buildFailure("Users not found");
        }

        final List<UserDTO> response = applicationUsers
                .stream()
                .map(applicationUser -> modelMapper.map(applicationUser, UserDTO.class))
                .collect(Collectors.toList());

        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USERS_FOUND_SUCCESS, response);
    }

    @Override
    public GenericResponse getApplicationUserById(Long id) {

        Optional<ApplicationUser> applicationUser = applicationUserRepository.findById(id);

        if (!applicationUser.isPresent()) {
            return ResponseBuilder.buildFailure("User not found");
        }

        UserDTO response = modelMapper.map(applicationUser.get(), UserDTO.class);
        return ResponseBuilder.buildSuccess("User fetched Successfully", response);
    }

    @Override
    public GenericResponse saveApplicationUser(SaveUserRequest request) {

        ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);
        applicationUser.setCreatedBy(new ApplicationUser(1L));
        log.debug("Saving applicationuser : {}", applicationUser);
        applicationUserRepository.save(applicationUser);

        return ResponseBuilder.buildSuccess("User Saved Successfully");
    }

    @Override
    public GenericResponse updateApplicationUser(Long id, UpdateUserRequest request) {

        Optional<ApplicationUser> optionalApplicationUser = applicationUserRepository.findById(id);

        if (!optionalApplicationUser.isPresent()) {
            return ResponseBuilder.buildFailure("User not found");
        }

        ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);
        applicationUser.setId(id);
        applicationUser.setCreatedBy(new ApplicationUser(1L));
        applicationUser.setLastModifiedBy(new ApplicationUser(1L));
        applicationUserRepository.save(applicationUser);

        return ResponseBuilder.buildSuccess("User Updated Successfully");
    }
}

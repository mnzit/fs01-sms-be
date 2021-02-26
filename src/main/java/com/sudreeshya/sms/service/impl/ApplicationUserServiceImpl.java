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
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository, ModelMapper modelMapper) {
        this.applicationUserRepository = applicationUserRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public GenericResponse getActiveApplicationUser(){
        final List<ApplicationUser> allUsers = applicationUserRepository.findAll();
        if(allUsers.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }
        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList = allUsers.stream()
                .map(applicationUser -> modelMapper.map(applicationUser, UserDTO.class))
                .collect(Collectors.toList());
        List<UserDTO> activeUsers = new ArrayList<>();
        for(UserDTO u : userDTOList){
            if(u.getIsActive() == 'Y'){
                activeUsers.add(u);
            }
        }
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, activeUsers);
    }

    @Override
    public GenericResponse getAllApplicationUser() {

        final List<ApplicationUser> applicationUsers = applicationUserRepository.findAll();
        log.info("applicationUsers: {}" , applicationUsers);

        if(applicationUsers.isEmpty()){
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
        if(!applicationUser.isPresent()){
            return ResponseBuilder.buildFailure("Could not find the user.");
        }
        else{
            UserDTO response = modelMapper.map(applicationUser.get(), UserDTO.class);
            if(response.getIsActive() == 'N'){
                return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_WAS_DELETED);
            }

            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, response);
        }
    }

    @Override
    public GenericResponse saveApplicationUser(SaveUserRequest request) {
        log.info("request: {}", request);
        ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);
        log.info("applicationUser: {}", applicationUser);
        applicationUser.setCreatedBy(new ApplicationUser(1L));
        applicationUser.setIsActive('Y');

        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();

        if(!applicationUserList.isEmpty()) {
            for (ApplicationUser a : applicationUserList) {
                if (a.getEmailAddress().equals(request.getEmailAddress())) {
                    return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_ALREADY_PRESENT);
                }
            }


            if (applicationUser.getFirstName().isEmpty() || applicationUser.getLastName().isEmpty() ||
                    applicationUser.getPassword().isEmpty()) {
                return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_CANT_BE_EMPTY);
            }
        }
        applicationUserRepository.save(applicationUser);

        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_SAVED);

    }

    @Override
    public GenericResponse updateApplicationUser(Long id, UpdateUserRequest request) {
        log.info("request: {}", request);
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        if(!applicationUserOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }

        else {
            ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);

            if(applicationUser.getFirstName().isEmpty() || applicationUser.getLastName().isEmpty() ||
                    applicationUser.getPassword().isEmpty()){
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
        if(!applicationUserOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }

        else{
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
        if(applicationUsers.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }
        List<UserDTO> userDTOList = new ArrayList<>();

        userDTOList = applicationUsers
                .stream()
                .map(appUsers -> modelMapper.map(appUsers, UserDTO.class))
                .collect(Collectors.toList());

        log.info("userDTOList: {}", userDTOList);

        List<UserDTO> usersTrash = new ArrayList<>();

        for (UserDTO userDTO: userDTOList) {
            if(userDTO.getIsActive() == 'N'){
                usersTrash.add(userDTO);
            }
        }
        log.info("Trash: {}", usersTrash);

        if(usersTrash.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.NO_TRASH);
        }
        else{
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, usersTrash);
        }

  }
    @Override
    public GenericResponse rollBackDeletedUsers(Long id){
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        if(!applicationUserOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }
        if(applicationUserOptional.get().getIsActive() == 'Y'){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_IN_TRASH);
        }
        ApplicationUser deletedApplicationUser = new ApplicationUser();
        deletedApplicationUser = modelMapper.map(applicationUserOptional.get(), ApplicationUser.class);
        deletedApplicationUser.setId(id);
        deletedApplicationUser.setIsActive('Y');
        applicationUserRepository.save(deletedApplicationUser);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_ROLLEDBACK);
    }

}


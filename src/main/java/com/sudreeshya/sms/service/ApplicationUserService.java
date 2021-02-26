package com.sudreeshya.sms.service;

import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.SaveUserRequest;
import com.sudreeshya.sms.request.UpdateUserRequest;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface ApplicationUserService {

    GenericResponse getActiveApplicationUser();

    GenericResponse getAllApplicationUser();

    GenericResponse getApplicationUserById(Long id);

    GenericResponse saveApplicationUser(SaveUserRequest request);

    GenericResponse updateApplicationUser(Long id, UpdateUserRequest request);

    GenericResponse deleteApplicationUser(Long id);

    GenericResponse findDeletedUsers();

    GenericResponse rollBackDeletedUsers(Long id);

    GenericResponse rollBackAllUsers();
}

package com.sudreeshya.sms.service;

import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.SaveUserRequest;
import com.sudreeshya.sms.request.UpdateUserRequest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface ApplicationUserService {

    GenericResponse getAllApplicationUser();

    GenericResponse getApplicationUserById(Long id);

    GenericResponse saveApplicationUser(SaveUserRequest request);

    GenericResponse updateApplicationUser(Long id, UpdateUserRequest request);

    GenericResponse deleteApplicationUser(Long id);

    GenericResponse findDeletedUsers();

    ByteArrayOutputStream download();
}

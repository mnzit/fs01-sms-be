package com.sudreeshya.sms.service;

import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.AuthRequest;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface AuthenticationService {

    GenericResponse login(AuthRequest request);
}

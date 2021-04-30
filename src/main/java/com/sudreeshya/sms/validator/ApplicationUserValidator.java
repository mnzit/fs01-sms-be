package com.sudreeshya.sms.validator;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.ResponseMsgConstant;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.model.ApplicationUser;

import java.util.List;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
public class ApplicationUserValidator {
    public static GenericResponse validateForSave(List<ApplicationUser> applicationUserList, String emailAddress) {
        if (!applicationUserList.isEmpty()) {
            for (ApplicationUser a : applicationUserList) {
                if (a.getEmailAddress().equals(emailAddress)) {
                    return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_ALREADY_PRESENT);
                }
            }
        }
        return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
    }
}

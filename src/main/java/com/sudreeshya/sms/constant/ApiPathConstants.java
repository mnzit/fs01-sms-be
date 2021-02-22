package com.sudreeshya.sms.constant;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface ApiPathConstants {

    String USERS = "users";

    interface PathVariable {

        String USERID = "userId";
        String COURSEID = "courseId";

        String USERID_WRAPPER = "{" + PathVariable.USERID + "}";
        String COURSEID_WRAPPER = "{" + PathVariable.COURSEID + "}";
    }

    interface SharedOperations{
        String SAVE = "save";
        String UPDATE = "update";
    }
}

package com.sudreeshya.sms.constant;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface ApiPathConstants {

    String USERS = "users";
    String COURSES = "courses";
    String SUBJECTS = "subjects";


    interface PathVariable {

        String USERID = "userId";
        String COURSEID = "courseId";
        String SUBJECTID = "subjectId";

        String USERID_WRAPPER = "{" + PathVariable.USERID + "}";
        String COURSEID_WRAPPER = "{" + PathVariable.COURSEID + "}";
        String SUBJECTID_WRAPPER = "{"+PathVariable.SUBJECTID+"}";
    }

    interface SharedOperations{
        String SAVE = "save";
        String UPDATE = "update";
        String DELETE = "delete";
        String TRASH = "trash";
    }
}

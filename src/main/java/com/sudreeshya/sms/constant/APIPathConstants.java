package com.sudreeshya.sms.constant;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface APIPathConstants {
    String USERS = "users";
    String COURSES = "courses";
    String SUBJECTS = "subjects";
    String LOGIN = "login";
    String ROLES = "roles";
    String ATTENDANCE = "attendances";

    interface PathVariable{
        String USERID_WRAPPER = "{userId}";
        String COURSEID_WRAPPER = "{courseId}";
        String USERID = "userId";
        String COURSEID = "courseId";
        String SUBJECTID_WRAPPER = "{subjectId}";
        String SUBJECTID = "subjectId";
    }

    interface SharedOperations{
        String SAVE ="save";
        String UPDATE = "update";
        String DELETE = "delete";
        String TRASH = "trash";
        String DOWNLOAD = "download";
    }
}

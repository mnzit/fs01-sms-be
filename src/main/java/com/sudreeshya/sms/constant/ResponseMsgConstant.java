package com.sudreeshya.sms.constant;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface ResponseMsgConstant {

    String USER_SAVED = "Users are stored in Database successfully!";
    String USER_UPDATED = "User was updated successfully!";
    String USER_NOT_FOUND = "Could not find the User!";
    String USER_FOUND = "User was found in the database!";
    String USER_WAS_DELETED = "The User you were looking for was deleted!";
    String USER_DELETED = "The User is deleted successfully!";
    String NO_TRASH = "No users are in Trash Bin!";
    String USER_ALREADY_PRESENT = "The email address you used is already registered!";
    String USER_CANT_BE_EMPTY ="Name/Password can't be empty!";
    String USER_NOT_IN_TRASH = "User is not in Trash. It is already active!";
    String USER_ROLLEDBACK = "User is rolledBack successfully!";
    String ALL_USERS_ROLLEDBACK = "All deleted Users are rolled back successfully!";


    String COURSE_SAVED = "Courses are stored in Database successfully!";
    String COURSE_UPDATED = "Course was updated successfully!";
    String COURSE_NOT_FOUND = "Could not find the Course!";
    String COURSE_FOUND = "Course was found in the database!";
    String COURSE_WAS_DELETED = "The Subject you were looking for was deleted!";
    String COURSE_CANT_BE_EMPTY = "Courses Name/Description/Code can't be left empty!";
    String COURSE_NOT_IN_TRASH = "Course is not in Trash. It is already active!";
    String COURSE_ROLLEDBACK = "Course is rolledBack successfully!";
    String ALL_COURSES_ROLLEDBACK = "All Courses are rolledBack successfully!";


    String SUBJECT_SAVED = "Subjects are stored in Database successfully!";
    String SUBJECT_UPDATED = "Subjects was updated successfully!";
    String SUBJECT_NOT_FOUND = "Could not find the Subject!";
    String SUBJECT_FOUND = "Subject was found in the database!";
    String SUBJECT_WAS_DELETED = "The Subject you were looking for was deleted!";
    String SUBJECT_CANT_BE_EMPTY = "Subjects Name/Description/Code can't be left empty!";
    String SUBJECT_NOT_IN_TRASH = "Subject is not in Trash. It is already active!";
    String SUBJECT_ROLLEDBACK = "Subject is rolledBack successfully!";
    String ALL_SUBJECTS_ROLLEDBACK = "All Subjects are rolledBack successfully";

    String EMAIL_PASSWORD_INCORRECT = "The username/password you entered is incorrect!";
    String LOGIN_SUCCESSFUL = "The login was successful!";
    String AUTH_SUCCESSFUL = "Authentication was successful!";
}

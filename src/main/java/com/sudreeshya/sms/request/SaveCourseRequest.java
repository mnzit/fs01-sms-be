package com.sudreeshya.sms.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SaveCourseRequest {
    private String name;
    private String description;
    private String code;

}

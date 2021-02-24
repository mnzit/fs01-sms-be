package com.sudreeshya.sms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveSubjectRequest {
    private String name;
    private String description;
    private String code;
}

package com.sudreeshya.sms.async.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Getter
@Setter
@AllArgsConstructor
public class EmailEvent {

    private String email;
    private String subject;
    private String message;
}

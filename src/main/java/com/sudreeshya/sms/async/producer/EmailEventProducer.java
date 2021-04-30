package com.sudreeshya.sms.async.producer;

import com.sudreeshya.sms.async.event.EmailEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Component
@AllArgsConstructor
public class EmailEventProducer {

    private final ApplicationContext applicationContext;

    public void sendEmail(EmailEvent emailEvent){
        applicationContext.publishEvent(emailEvent);
    }
}

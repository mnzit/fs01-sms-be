package com.sudreeshya.sms.async.consumer;

import com.sudreeshya.sms.async.event.EmailEvent;
import com.sudreeshya.sms.mail.Mailer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
@Component
@AllArgsConstructor
public class EmailEventConsumer {

    private final Mailer mailer;

    @Async
    @EventListener
    public void receiveEmail(EmailEvent emailEvent){
        log.debug("ASYNC EMAIL STARTED");
        mailer.sendEmail(emailEvent.getEmail(),emailEvent.getSubject(),emailEvent.getMessage());
        log.debug("ASYNC EMAIL ENDED");
    }
}

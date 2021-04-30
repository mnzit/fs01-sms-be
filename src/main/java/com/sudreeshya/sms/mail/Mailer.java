package com.sudreeshya.sms.mail;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Component
@AllArgsConstructor
public class Mailer {

    private final JavaMailSender emailSender;

    public void sendEmail(String email, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("fakerfaker@2054gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }
}

package com.sudreeshya.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.*;
import java.util.Properties;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Session session = Session.getInstance(getMailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSender.getUsername(), mailSender.getPassword());
            }
        });

        try {
            Transport transport = session.getTransport("smtp");

            transport.connect("smtp.gmail.com", "fakerfaker2054@gmail.com", "nepali@123N");

            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(465);
            mailSender.setUsername("fakerfaker2054@gmail.com");
            mailSender.setPassword("nepali@123N");
            mailSender.setJavaMailProperties(getMailProperties());
            mailSender.setSession(session);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return mailSender;
    }


    private Properties getMailProperties() {
        Properties properties = new Properties();

        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required", "false");
        properties.setProperty("mail.smtp.auth", "false");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        return properties;
    }
}

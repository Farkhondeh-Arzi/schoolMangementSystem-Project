package com.example.sm.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class Mail {

    @Autowired
    JavaMailSender mailSender;

    public void send(SubscribeForm subscriber, String filePath) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setSubject("Users");
        helper.setTo(subscriber.getEmail());
        helper.setText("", true);

        helper.addAttachment("export.zip", new ClassPathResource(filePath));

        mailSender.send(msg);
    }
}


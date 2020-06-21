package com.greenapp.mailservice.services;

import com.greenapp.mailservice.dto.TwoFaDTO;
import com.greenapp.mailservice.config.MailPropsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.greenapp.mailservice.config.MailConstants.PASSWORD;
import static com.greenapp.mailservice.config.MailConstants.USERNAME;


@Service
@Slf4j
public class EmailService extends Authenticator {

    private final MailPropsProvider propsProvider;

    @Autowired
    public EmailService(MailPropsProvider propsProvider) {
        this.propsProvider = propsProvider;
    }

    @Async
    public void send2Fa(TwoFaDTO user) {

        var session = Session.getInstance(propsProvider.getMailProps(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));
            message.setSubject("Green App auth");

            message.setText(String.format(
                    "Your verification code: %s \nRegards,\nGreenApp team."
                    , user.getTwoFaCode()));

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}

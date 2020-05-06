package com.greenapp.mailservice.services;

import com.greenapp.mailservice.config.MailPropsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

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

    private String generate2FaCode() {
        return String.valueOf(new Random().nextInt(9999) + 1000);
    }

    @Async
    public void sendEmail(final String to) {

        Session session = Session.getInstance(propsProvider.getMailProps(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Green App auth");

            //todo change text to html
            message.setText(String.format(
                    """ 
                            Your verification code:  %s
                                                       
                            Regards, 
                            GreenApp team.
                                         
                            """
                    , generate2FaCode()));

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}

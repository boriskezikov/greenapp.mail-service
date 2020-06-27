package com.greenapp.mailservice.services;

import com.greenapp.mailservice.dto.CredentialsDTO;
import com.greenapp.mailservice.dto.RewardMailDTO;
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

        try {
            MimeMessage message = new MimeMessage(getSession());
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

    public void resetPassword(CredentialsDTO creds) {

        try {
            MimeMessage message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(creds.getUsername()));
            message.setSubject("GreenApp Password changed");

            message.setText(String.format(
                    "Hi! Your password has been updated!" +
                            "\nYour credentials now: " +
                            "\n\tUsername: %s" +
                            "\n\tPassword: %s  " +
                            "\nRegards,\nGreenApp team."
                    , creds.getUsername(), creds.getPassword()));

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    public void sendConfirmation(RewardMailDTO rewardMailDTO) {
        try {
            MimeMessage message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(rewardMailDTO.getMailAddress()));
            message.setSubject("GreenApp Confirmation");

            message.setText(String.format(
                    "Dear %s! You have successfully bought reward item: %s" +
                            "\n\tYour item price: %s " +
                            "\n\tDescription: %s" +
                            "\nRegards,\nGreenApp team."
                    , rewardMailDTO.getSurname() + rewardMailDTO.getName(),
                    rewardMailDTO.getRewardTitle(),
                    rewardMailDTO.getRewardPrice(),
                    rewardMailDTO.getRewardDescription()));

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }


    private Session getSession() {
        return Session.getInstance(propsProvider.getMailProps(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }
}

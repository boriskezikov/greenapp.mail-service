package com.greenapp.mailservice.kafka;

import com.greenapp.mailservice.dto.CredentialsDTO;
import com.greenapp.mailservice.dto.TwoFaDTO;
import com.greenapp.mailservice.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.greenapp.mailservice.kafka.MailTopics.MAIL_2FA_TOPIC;
import static com.greenapp.mailservice.kafka.MailTopics.PASSWORD_RESET;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceListeners {

    private final EmailService emailService;

    @KafkaListener(topics = MAIL_2FA_TOPIC)
    public void signUp(TwoFaDTO dtoTest) {
        log.info(String.format("Received message at topic: %s.\t Request for two-factor auth. Mail: %s",
                MAIL_2FA_TOPIC,
                dtoTest.getMail()));
        emailService.send2Fa(dtoTest);
    }

    @KafkaListener(topics = PASSWORD_RESET)
    public void passwordResetNotify(CredentialsDTO credentialsDTO){
        log.info(String.format("Received message at topic: %s.\t Password change notification. Mail: %s",
                PASSWORD_RESET,
                credentialsDTO.getUsername()));
        emailService.resetPassword(credentialsDTO);
    }
}

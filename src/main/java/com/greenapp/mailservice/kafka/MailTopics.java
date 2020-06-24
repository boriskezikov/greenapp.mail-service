package com.greenapp.mailservice.kafka;


import static com.greenapp.mailservice.config.KafkaConfigConstants.TOPIC_PREFIX;

public interface MailTopics {
    String MAIL_2FA_TOPIC = TOPIC_PREFIX + "mail-auth2fa-account-created";
    String PASSWORD_RESET = TOPIC_PREFIX + "password-reset";
}

package com.greenapp.mailservice.kafka;

import com.greenapp.mailservice.dto.RewardMailDTO;
import com.greenapp.mailservice.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.greenapp.mailservice.kafka.MailTopics.REWARD_CONFIRMATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopListeners {

    private final EmailService emailService;

    @KafkaListener(topics = REWARD_CONFIRMATION)
    public void sendConfirmation(RewardMailDTO mailDTO) {
        emailService.sendConfirmation(mailDTO);
    }
}

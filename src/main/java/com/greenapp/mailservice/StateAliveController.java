package com.greenapp.mailservice;

import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class StateAliveController {

    @GetMapping("/live")
    public void leave(){
        System.out.println("Leave request accepted.");
    }

    @Scheduled(cron = "* */15 * * * *")
    public void keepGatewayAlivePlease() {

        var restTemplate = new RestTemplate();
        restTemplate.exchange(
                "https://greenapp-gateway.herokuapp.com/live",
                HttpMethod.GET,
                null,
                Void.class
        );
        System.out.println("Sent alive");
    }
}

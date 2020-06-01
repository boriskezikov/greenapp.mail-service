package com.greenapp.mailservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateAliveController {

    @GetMapping("/leave")
    public void leave(){
        System.out.println("Leave request accepted.");
    }
}

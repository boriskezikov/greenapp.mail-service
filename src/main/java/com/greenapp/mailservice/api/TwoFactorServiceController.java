package com.greenapp.mailservice.api;

import com.greenapp.mailservice.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/2fa")
public class TwoFactorServiceController {

    private final EmailService emailService;

    @Async
    @GetMapping("/{emailId}")
    public CompletableFuture<ResponseEntity<Void>> send2faCodeInEmail(@PathVariable("emailId") String emailId) {
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
    }

}

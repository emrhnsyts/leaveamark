package com.emrhnsyts.leaveamark.controller;

import com.emrhnsyts.leaveamark.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/emails")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/{token}")
    public ResponseEntity verifyEmail(@PathVariable("token") String token) {
        emailService.verifyEmail(token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

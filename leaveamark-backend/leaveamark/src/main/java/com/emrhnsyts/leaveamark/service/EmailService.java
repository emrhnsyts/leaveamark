package com.emrhnsyts.leaveamark.service;

import com.emrhnsyts.leaveamark.entity.AppUser;
import com.emrhnsyts.leaveamark.entity.EmailVerification;
import com.emrhnsyts.leaveamark.exception.GenericException;
import com.emrhnsyts.leaveamark.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailSenderService emailSenderService;

    public void verifyEmail(String token) {
        EmailVerification emailVerification = getEmailVerificationByToken(token);
        emailVerification.getAppUser().setEnabled(true);
        emailVerificationRepository.delete(emailVerification);
    }

    protected void sendEmailTo(AppUser appUser) {
        EmailVerification emailVerification = addEmailVerification(appUser);
        emailSenderService.sendEmail(appUser.getEmail(),
                "Leave a Mark Account Verification",
                "Hi there " + appUser.getUsername() + "!" + " In order to enable your" +
                        " account please click the link below.\n"
                        + "http://localhost:3000/verify/"
                        + emailVerification.getToken());
    }

    private EmailVerification getEmailVerificationByToken(String token) {
        Optional<EmailVerification> optionalEmailVerification = emailVerificationRepository.findByToken(token);
        if (optionalEmailVerification.isPresent()) {
            return optionalEmailVerification.get();
        } else {
            throw new GenericException("Token not found.");
        }
    }

    private EmailVerification addEmailVerification(AppUser appUser) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setToken(UUID.randomUUID().toString());
        emailVerification.setAppUser(appUser);
        return emailVerificationRepository.save(emailVerification);
    }
}

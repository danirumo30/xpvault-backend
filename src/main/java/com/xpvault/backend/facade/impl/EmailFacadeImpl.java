package com.xpvault.backend.facade.impl;

import com.xpvault.backend.facade.EmailFacade;
import com.xpvault.backend.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailFacadeImpl implements EmailFacade {

    private final EmailService emailService;

    @Override
    public void sendVerificationEmail(String to, String subject, String body) throws MessagingException {
        emailService.sendVerificationEmail(to, subject, body);
    }
}

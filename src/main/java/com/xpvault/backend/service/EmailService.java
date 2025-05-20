package com.xpvault.backend.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendVerificationEmail(String to, String subject, String body) throws MessagingException;

}

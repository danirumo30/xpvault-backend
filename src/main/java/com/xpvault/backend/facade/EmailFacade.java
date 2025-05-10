package com.xpvault.backend.facade;

import jakarta.mail.MessagingException;

public interface EmailFacade {

    void sendVerificationEmail(String to, String subject, String body) throws MessagingException;

}

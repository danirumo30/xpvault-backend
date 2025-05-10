package com.xpvault.backend.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    /**
     * Envía un correo electrónico con contenido HTML al usuario para la verificación de cuenta.
     *
     * @param to      Dirección de correo del destinatario
     * @param subject Asunto del mensaje
     * @param body    Contenido del mensaje (HTML)
     * @throws MessagingException si ocurre un error durante la creación o envío del mensaje
     */
    void sendVerificationEmail(String to, String subject, String body) throws MessagingException;

}

package com.xpvault.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de enviar correos electrónicos.
 * Utiliza {@link JavaMailSender} para enviar mensajes MIME,
 * incluyendo contenido HTML para casos como la verificación de usuarios.
 */
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo electrónico con contenido HTML al usuario para la verificación de cuenta.
     *
     * @param to      Dirección de correo del destinatario
     * @param subject Asunto del mensaje
     * @param body    Contenido del mensaje (HTML)
     * @throws MessagingException si ocurre un error durante la creación o envío del mensaje
     */
    public void sendVerificationEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }
}

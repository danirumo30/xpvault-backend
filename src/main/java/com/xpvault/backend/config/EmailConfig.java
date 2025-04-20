package com.xpvault.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Clase de configuración para el envío de correos electrónicos utilizando JavaMailSender.
 * Se configura para utilizar el servidor SMTP de Gmail.
 */
@Configuration
public class EmailConfig {
    // Inyección del nombre de usuario del correo desde application.properties
    @Value("${spring.mail.username}")
    private String emailUsername;

    // Inyección de la contraseña del correo desde application.properties
    @Value("${spring.mail.password}")
    private String password;

    /**
     * Bean que configura y retorna una instancia de JavaMailSender.
     *
     * @return instancia configurada de JavaMailSender para el envío de correos
     */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(emailUsername);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}

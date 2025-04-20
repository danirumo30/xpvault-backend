package com.xpvault.backend.service;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.dto.RegisterUserDTO;
import com.xpvault.backend.dto.VerifyUserDTO;
import com.xpvault.backend.entity.AppUser;
import com.xpvault.backend.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/**
 * Servicio encargado de gestionar el registro, autenticación, verificación
 * y envío de correos electrónicos para los usuarios de la aplicación.
 */
@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    /**
     * Registra a un nuevo usuario, codifica su contraseña, genera un código
     * de verificación y envía un email de verificación.
     *
     * @param input DTO con los datos del usuario a registrar
     * @return Usuario creado y almacenado en la base de datos
     */
    public AppUser signUp(RegisterUserDTO input) {
        AppUser user = new AppUser(input.getUsername(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        sendVerificationEmail(user);
        return userRepository.save(user);
    }

    /**
     * Autentica a un usuario por su email y contraseña.
     * Lanza error si no está verificado.
     *
     * @param input DTO con las credenciales del usuario
     * @return Usuario autenticado
     */
    public AppUser authenticate(LoginUserDTO input) {
        AppUser user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Account Not verified. Please verify your account.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return user;
    }

    /**
     * Verifica el código enviado por el usuario y habilita su cuenta.
     *
     * @param input DTO con email y código de verificación
     */
    public void verifyUser(VerifyUserDTO input) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            if (user.getVerificationExpiration().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("User Verification Expired");
            }
            if (user.getVerificationCode().equals(input.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationExpiration(null);
                userRepository.save(user);
            } else {
                throw new RuntimeException("User Verification Code Incorrect");
            }
        } else {
            throw new RuntimeException("User Not Found");
        }
    }

    /**
     * Reenvía un nuevo código de verificación si la cuenta no está habilitada.
     *
     * @param email Email del usuario
     */
    public void resendVerificationCode(String email) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account Already Verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpiration(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User Not Found");
        }
    }

    /**
     * Envía el email de verificación al usuario con código en formato HTML.
     *
     * @param user Usuario al que se le enviará el correo
     */
    public void sendVerificationEmail(AppUser user) {
        String subject = "Account Verification";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}

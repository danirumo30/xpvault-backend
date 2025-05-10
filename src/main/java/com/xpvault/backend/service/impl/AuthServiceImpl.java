package com.xpvault.backend.service.impl;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.exception.*;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.dao.UserDAO;
import com.xpvault.backend.service.AuthService;
import com.xpvault.backend.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final Random random = new Random();

    public AppUserModel signUp(AppUserModel appUserModel) {
        appUserModel.setVerificationCode(generateVerificationCode());
        appUserModel.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));
        appUserModel.setEnabled(false);
        sendVerificationEmail(appUserModel);
        return userDAO.save(appUserModel);
    }

    public AppUserModel authenticate(LoginUserDTO loginUserDTO) {
        AppUserModel user = userDAO.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (!user.isEnabled()) {
            throw new AccountNotVerifiedException("Account Not verified. Please verify your account.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDTO.getEmail(),
                        loginUserDTO.getPassword()
                )
        );
        return user;
    }

    public void verifyUser(AppUserModel appUserModel) {
        Optional<AppUserModel> optionalUser = userDAO.findByEmail(appUserModel.getEmail());
        if (optionalUser.isPresent()) {
            AppUserModel user = optionalUser.get();
            if (user.getVerificationExpiration().isBefore(LocalDateTime.now())) {
                throw new VerificationCodeExpiredException("User Verification Expired");
            }
            if (user.getVerificationCode().equals(appUserModel.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationExpiration(null);
                userDAO.save(user);
            } else {
                throw new IncorrectVerificationCodeException("User Verification Code Incorrect");
            }
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<AppUserModel> optionalUser = userDAO.findByEmail(email);
        if (optionalUser.isPresent()) {
            AppUserModel user = optionalUser.get();
            if (user.isEnabled()) {
                throw new AccountAlreadyVerifiedException("Account Already Verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpiration(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userDAO.save(user);
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    public void sendVerificationEmail(AppUserModel user) {
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
            throw new EmailSendingException(e.getMessage());
        }
    }

    private String generateVerificationCode() {
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}

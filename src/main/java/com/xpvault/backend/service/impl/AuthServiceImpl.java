package com.xpvault.backend.service.impl;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.exception.*;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.dao.UserDAO;
import com.xpvault.backend.service.AuthService;
import com.xpvault.backend.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.Getter;
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
@Getter(AccessLevel.PROTECTED)
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
        appUserModel.setPassword(passwordEncoder.encode(appUserModel.getPassword()));
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
                + "<head>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #171a21;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "    color: #fff;"
                + "} "
                + ".container {"
                + "    width: 100%;"
                + "    padding: 20px;"
                + "    background-color: #171a21;"
                + "    display: flex;"
                + "    justify-content: center;"
                + "} "
                + ".email-content {"
                + "    background-color: #1B1F22;"
                + "    padding: 30px;"
                + "    width: 100%;"
                + "    max-width: 600px;"
                + "    border-radius: 10px;"
                + "    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);"
                + "    text-align: center;"
                + "} "
                + ".header {"
                + "    margin-bottom: 20px;"
                + "} "
                + ".logo {"
                + "    width: 150px;"
                + "    margin-bottom: 20px;"
                + "border-radius: 50%;"
                + "} "
                + ".verification-code {"
                + "    font-size: 24px;"
                + "    font-weight: bold;"
                + "    color: #00B2FF;"
                + "    background-color: #1B1F22;"
                + "    padding: 15px;"
                + "    border-radius: 8px;"
                + "} "
                + ".footer {"
                + "    margin-top: 30px;"
                + "    font-size: 14px;"
                + "    color: #777;"
                + "} "
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class=\"container\">"
                + "    <div class=\"email-content\">"
                + "        <div class=\"header\">"
                + "            <img src=\"https://i.imgur.com/WZPHmOw.jpeg\" class=\"logo\" alt=\"Logo\" />"
                + "            <h2 style=\"color: #00B2FF;\">Welcome to XP-VAULT!</h2>"
                + "        </div>"
                + "        <p style=\"font-size: 18px; color: #fff;\">Please enter the verification code below to complete your account setup.</p>"
                + "        <div class=\"verification-code\">"
                + "            " + verificationCode
                + "        </div>"
                + "        <div class=\"footer\">"
                + "            <p>If you did not request this, please ignore this email.</p>"
                + "            <p>For any issues, contact our support at <a href=\"mailto:support@yourdomain.com\" style=\"color: #00B2FF;\">support@yourdomain.com</a></p>"
                + "        </div>"
                + "    </div>"
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

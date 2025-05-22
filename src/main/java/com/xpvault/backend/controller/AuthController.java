package com.xpvault.backend.controller;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.dto.RegisterUserDTO;
import com.xpvault.backend.dto.VerifyUserDTO;
import com.xpvault.backend.facade.AuthFacade;
import com.xpvault.backend.facade.JwtFacade;
import com.xpvault.backend.response.LoginResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.xpvault.backend.literals.constants.AppConstants.*;

@RestController
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class AuthController {

    private final JwtFacade jwtFacade;
    private final AuthFacade authFacade;

    @PostMapping(REGISTER_PATH)
    public ResponseEntity<RegisterUserDTO> register(@RequestBody RegisterUserDTO registerUserDTO) {
        RegisterUserDTO registeredUserDTO = authFacade.signUp(registerUserDTO);
        return ResponseEntity.ok(registeredUserDTO);
    }

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDTO loginUserDTO) {
        LoginUserDTO authenticated = authFacade.authenticate(loginUserDTO);
        String token = jwtFacade.generateToken(authenticated);
        LoginResponse loginResponse = new LoginResponse(token, jwtFacade.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(VERIFY_PATH)
    public ResponseEntity<String> verify(@RequestBody VerifyUserDTO verifyUserDTO) {
        try {
            authFacade.verifyUser(verifyUserDTO);
            return ResponseEntity.ok(SUCCESS_VERIFY);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(RESEND_PATH)
    public ResponseEntity<String> resend(@RequestBody String email) {
        try {
            authFacade.resendVerificationCode(email);
            return ResponseEntity.ok(SUCCESS_RESEND);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

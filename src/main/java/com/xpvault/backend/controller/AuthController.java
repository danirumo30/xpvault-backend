package com.xpvault.backend.controller;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.dto.RegisterUserDTO;
import com.xpvault.backend.dto.VerifyUserDTO;
import com.xpvault.backend.facade.AuthFacade;
import com.xpvault.backend.facade.JwtFacade;
import com.xpvault.backend.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtFacade jwtFacade;
    private final AuthFacade authFacade;

    /**
     * Endpoint para registrar un nuevo usuario en la plataforma.
     *
     * @param registerUserDTO DTO con la información de registro
     * @return Usuario registrado (sin contraseña)
     */
    @PostMapping("/signup")
    public ResponseEntity<RegisterUserDTO> register(@RequestBody RegisterUserDTO registerUserDTO) {
        RegisterUserDTO registeredUserDTO = authFacade.signUp(registerUserDTO);
        return ResponseEntity.ok(registeredUserDTO);
    }

    /**
     * Endpoint para iniciar sesión de un usuario registrado.
     *
     * @param loginUserDTO DTO con email y contraseña
     * @return LoginResponse con el token JWT y su tiempo de expiración
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDTO loginUserDTO) {
        LoginUserDTO authenticated = authFacade.authenticate(loginUserDTO);
        String token = jwtFacade.generateToken(authenticated);
        LoginResponse loginResponse = new LoginResponse(token, jwtFacade.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Endpoint para verificar la cuenta de un usuario mediante código.
     *
     * @param verifyUserDTO DTO con el correo electrónico y el código recibido
     * @return Mensaje de éxito o excepción si el código no es válido
     */
    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyUserDTO verifyUserDTO) {
        try {
            authFacade.verifyUser(verifyUserDTO);
            return ResponseEntity.ok("Account verified successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para reenviar el código de verificación a un usuario.
     *
     * @param email Email del usuario (como cadena JSON)
     * @return Mensaje de éxito o error si el email no es válido o no existe
     */
    @PostMapping("/resend")
    public ResponseEntity<String> resend(@RequestBody String email) {
        try {
            authFacade.resendVerificationCode(email);
            return ResponseEntity.ok("Account resend successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

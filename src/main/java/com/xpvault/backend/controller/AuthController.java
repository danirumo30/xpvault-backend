package com.xpvault.backend.controller;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.dto.RegisterUserDTO;
import com.xpvault.backend.dto.VerifyUserDTO;
import com.xpvault.backend.entity.AppUser;
import com.xpvault.backend.response.LoginResponse;
import com.xpvault.backend.service.AuthService;
import com.xpvault.backend.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que maneja las operaciones relacionadas con la autenticación de usuarios.
 * Rutas: /auth/signup, /auth/login, /auth/verify, /auth/resend
 */
@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;

    /**
     * Endpoint para registrar un nuevo usuario en la plataforma.
     *
     * @param registerUserDTO DTO con la información de registro
     * @return Usuario registrado (sin contraseña)
     */
    @PostMapping("/signup")
    public ResponseEntity<AppUser> register(@RequestBody RegisterUserDTO registerUserDTO) {
        AppUser registeredUser = authService.signUp(registerUserDTO);
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Endpoint para iniciar sesión de un usuario registrado.
     *
     * @param loginUserDTO DTO con email y contraseña
     * @return LoginResponse con el token JWT y su tiempo de expiración
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDTO loginUserDTO) {
        AppUser authenticatedUser = authService.authenticate(loginUserDTO);
        String token = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(token, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Endpoint para verificar la cuenta de un usuario mediante código.
     *
     * @param verifyUserDTO DTO con el correo electrónico y el código recibido
     * @return Mensaje de éxito o excepción si el código no es válido
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyUserDTO verifyUserDTO) {
        try {
            authService.verifyUser(verifyUserDTO);
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
    public ResponseEntity<?> resend(@RequestBody String email) {
        try {
            authService.resendVerificationCode(email);
            return ResponseEntity.ok("Account resend successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

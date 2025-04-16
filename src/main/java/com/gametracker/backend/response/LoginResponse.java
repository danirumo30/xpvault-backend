package com.gametracker.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la respuesta devuelta al cliente tras un inicio de sesión exitoso.
 * Contiene el token JWT y el tiempo de expiración del mismo.
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private long expiresIn;
}

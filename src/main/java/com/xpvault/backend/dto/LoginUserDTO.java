package com.gametracker.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para transferir los datos del formulario de inicio de sesión.
 */
@Data
@NoArgsConstructor
public class LoginUserDTO {
    private String email;
    private String password;
}

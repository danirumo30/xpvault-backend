package com.gametracker.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para registrar un nuevo usuario en el sistema.
 */
@Data
@NoArgsConstructor
public class RegisterUserDTO {
    private String email;
    private String password;
    private String username;
}

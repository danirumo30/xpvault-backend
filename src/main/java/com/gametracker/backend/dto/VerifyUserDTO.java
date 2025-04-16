package com.gametracker.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para verificar una cuenta de usuario mediante un código de verificación.
 */
@Data
@NoArgsConstructor
public class VerifyUserDTO {
    private String email;
    private String verificationCode;
}

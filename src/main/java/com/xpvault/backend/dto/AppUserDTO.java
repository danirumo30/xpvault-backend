package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String verificationCode;
    private LocalDateTime verificationExpiration;
    private Boolean enabled;

}

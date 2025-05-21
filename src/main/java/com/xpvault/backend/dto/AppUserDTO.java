package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUserDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String verificationCode;
    private LocalDateTime verificationExpiration;
    private String steamUsername;

}

package com.xpvault.backend.facade;

import com.xpvault.backend.dto.LoginUserDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtFacade {

    String extractUsername(String token);
    String generateToken(LoginUserDTO loginUserDTO);
    long getExpirationTime();
    boolean isTokenValid(String token, UserDetails userDetails);

}

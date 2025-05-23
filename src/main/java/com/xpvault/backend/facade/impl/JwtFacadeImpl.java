package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.LoginUserDTOToAppUserModelConverter;
import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.facade.JwtFacade;
import com.xpvault.backend.service.JwtService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class JwtFacadeImpl implements JwtFacade {

    private final JwtService jwtService;
    private final LoginUserDTOToAppUserModelConverter loginUserDTOToAppUserModelConverter;

    @Override
    public String extractUsername(String token) {
        return jwtService.extractUsername(token);
    }

    @Override
    public String generateToken(LoginUserDTO loginUserDTO) {
        return jwtService.generateToken(loginUserDTOToAppUserModelConverter.convert(loginUserDTO));
    }

    @Override
    public long getExpirationTime() {
        return jwtService.getExpirationTime();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return jwtService.isTokenValid(token, userDetails);
    }
}

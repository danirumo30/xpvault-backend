package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.LoginUserDTOToAppUserModelConverter;
import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.facade.JwtFacade;
import com.xpvault.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtFacadeImpl implements JwtFacade {

    private final JwtService jwtService;
    private final LoginUserDTOToAppUserModelConverter loginUserDTOToAppUserModelConverter;

    @Override
    public String extractUsername(String token) {
        return jwtService.extractUsername(token);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return jwtService.extractClaim(token, claimsResolver);
    }

    @Override
    public String generateToken(LoginUserDTO loginUserDTO) {
        return jwtService.generateToken(loginUserDTOToAppUserModelConverter.convert(loginUserDTO));
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return jwtService.generateToken(extraClaims, userDetails);
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

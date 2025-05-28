package com.xpvault.backend.facade.impl;

import com.xpvault.backend.facade.SteamAuthFacade;
import com.xpvault.backend.service.SteamAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class SteamAuthFacadeImpl implements SteamAuthFacade {

    private final SteamAuthService steamAuthService;

    @Override
    public String getSteamRedirectUrl() {
        return steamAuthService.buildRedirectUrl(
                "http://localhost:5000/steam-auth/login/return",
                "http://localhost:5000"
        );
    }

    @Override
    public String processSteamReturn(HttpServletRequest request) throws IOException {
        return steamAuthService.verifySteamLogin(request);
    }
}


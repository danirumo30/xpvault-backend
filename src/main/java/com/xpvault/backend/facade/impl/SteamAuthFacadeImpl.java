package com.xpvault.backend.facade.impl;

import com.xpvault.backend.facade.SteamAuthFacade;
import com.xpvault.backend.service.SteamAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class SteamAuthFacadeImpl implements SteamAuthFacade {

    private final SteamAuthService steamAuthService;

    @Override
    public String getSteamRedirectUrl(String redirect) {
        String returnUrl = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("xpvaultbackend.es")
                .path("/steam-auth/login/return")
                .queryParam("redirect", redirect)
                .build()
                .toUriString();

        return steamAuthService.buildRedirectUrl(returnUrl, "https://xpvaultbackend.es");
    }

    @Override
    public String processSteamReturn(HttpServletRequest request) throws IOException {
        return steamAuthService.verifySteamLogin(request);
    }
}

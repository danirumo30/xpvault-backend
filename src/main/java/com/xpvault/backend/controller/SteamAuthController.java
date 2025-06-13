package com.xpvault.backend.controller;

import com.xpvault.backend.facade.SteamAuthFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.xpvault.backend.literals.constants.AppConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(STEAM_AUTH_PATH)
@Getter(AccessLevel.PROTECTED)
public class SteamAuthController {

    private final UserController userController;
    private final SteamAuthFacade steamAuthFacade;

    @GetMapping(LOGIN_PATH)
    public void redirectToSteam(
            @RequestParam(value = "redirect", required = false, defaultValue = "web") String redirect,
            HttpServletResponse response
    ) throws IOException {
        response.sendRedirect(steamAuthFacade.getSteamRedirectUrl(redirect));
    }


    @GetMapping(LOGIN_PATH + "/return")
    public void handleSteamReturn(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "redirect", required = false, defaultValue = "web") String redirect
    ) throws IOException {
        String steamId = steamAuthFacade.processSteamReturn(request);

        if (steamId == null) {
            response.sendRedirect(getRedirectUrl(redirect));
            return;
        }

        response.sendRedirect(getRedirectUrlWithSteamId(redirect, steamId));
    }

    private String getRedirectUrl(String redirect) {
        return switch (redirect.toLowerCase()) {
            case "android" -> "myapp://steam-login-error";
            case "ios"     -> "myapp-ios://steam-login-error";
            default        -> "https://xpvault.me/#steamError";
        };
    }

    private String getRedirectUrlWithSteamId(String redirect, String steamId) {
        return switch (redirect.toLowerCase()) {
            case "android" -> "myapp://steam-login-return?steamId=" + steamId;
            case "ios"     -> "myapp-ios://steam-login-return?steamId=" + steamId;
            default        -> "https://xpvault.me/?steamId=" + steamId;
        };
    }
}

package com.xpvault.backend.controller;

import com.xpvault.backend.facade.SteamAuthFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.xpvault.backend.literals.constants.AppConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(STEAM_AUTH_PATH)
@Getter(AccessLevel.PROTECTED)
public class SteamAuthController {

    private final SteamAuthFacade steamAuthFacade;

    @GetMapping(LOGIN_PATH)
    public void redirectToSteam(HttpServletResponse response) throws IOException {
        response.sendRedirect(steamAuthFacade.getSteamRedirectUrl());
    }

    @GetMapping(LOGIN_PATH + "/return")
    public ResponseEntity<Object> handleSteamReturn(HttpServletRequest request) throws IOException {
        String steamId = steamAuthFacade.processSteamReturn(request);

        if (steamId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(INVALID_STEAM_LOGIN);
        }

        return ResponseEntity.ok(steamId);
    }
}


package com.xpvault.backend.service;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface SteamAuthService {

    String buildRedirectUrl(String returnTo, String realm);
    String verifySteamLogin(HttpServletRequest request) throws IOException;

}


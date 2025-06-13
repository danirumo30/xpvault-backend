package com.xpvault.backend.facade;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface SteamAuthFacade {

    String getSteamRedirectUrl(String redirect);
    String processSteamReturn(HttpServletRequest request) throws IOException;

}


package com.xpvault.backend.service.impl;

import com.xpvault.backend.service.SteamAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class SteamAuthServiceImpl implements SteamAuthService {

    @Override
    public String buildRedirectUrl(String returnTo, String realm) {
        return "https://steamcommunity.com/openid/login" +
                "?openid.ns=http://specs.openid.net/auth/2.0" +
                "&openid.mode=checkid_setup" +
                "&openid.return_to=" + URLEncoder.encode(returnTo, StandardCharsets.UTF_8) +
                "&openid.realm=" + URLEncoder.encode(realm, StandardCharsets.UTF_8) +
                "&openid.identity=http://specs.openid.net/auth/2.0/identifier_select" +
                "&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select";
    }

    @Override
    public String verifySteamLogin(HttpServletRequest request) throws IOException {
        Map<String, String> params = request.getParameterMap()
                                            .entrySet()
                                            .stream()
                                            .collect(Collectors.toMap(
                                                    Map.Entry::getKey, e -> e.getValue()[0]
                                            ));

        String payload = params.entrySet()
                               .stream()
                               .filter(e -> !e.getKey().equals("openid.mode"))
                               .map(e ->
                                       URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "=" +
                                       URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8)
                               )
                               .collect(Collectors.joining("&", "openid.mode=check_authentication&", ""));

        URL url = URI.create("https://steamcommunity.com/openid/login").toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
        }

        String steamResponse = new BufferedReader(new InputStreamReader(conn.getInputStream())).lines()
                                                                                               .collect(Collectors.joining("\n"));

        if (steamResponse.contains("is_valid:true")) {
            return Optional.ofNullable(params.get("openid.claimed_id"))
                           .filter(claimedId ->
                                   claimedId.startsWith("https://steamcommunity.com/openid/id/")
                           )
                           .map(claimedId ->
                                   claimedId.substring(claimedId.lastIndexOf("/") + 1)
                           )
                           .orElse(null);
        }

        return null;
    }
}


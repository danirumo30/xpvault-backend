package com.xpvault.backend.exception;

public class SteamApiException extends RuntimeException {
    public SteamApiException(String message) {
        super(message);
    }
}

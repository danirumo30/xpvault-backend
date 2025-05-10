package com.xpvault.backend.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     *
     * @param token el token JWT
     * @return el nombre de usuario (subject)
     */
    String extractUsername(String token);

    /**
     * Extrae una reclamación específica del token JWT.
     *
     * @param token          el token JWT
     * @param claimsResolver una función para extraer la reclamación deseada
     * @param <T>            el tipo de la reclamación
     * @return el valor de la reclamación extraída
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Genera un token JWT para un usuario.
     *
     * @param userDetails los detalles del usuario para el que se generará el token
     * @return el token JWT generado
     */
    String generateToken(UserDetails userDetails);

    /**
     * Genera un token JWT con reclamaciones adicionales.
     *
     * @param extraClaims las reclamaciones adicionales que se agregarán al token
     * @param userDetails los detalles del usuario para el que se generará el token
     * @return el token JWT generado
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Verifica si el token JWT es válido.
     *
     * @param token       el token JWT
     * @param userDetails los detalles del usuario
     * @return {@code true} si el token es válido, {@code false} en caso contrario
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    long getExpirationTime();

}

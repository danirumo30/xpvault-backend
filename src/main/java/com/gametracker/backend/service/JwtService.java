package com.gametracker.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * Servicio encargado de la gestión de tokens JWT (JSON Web Token).
 * Proporciona métodos para generar, validar y extraer información de un token JWT.
 */
@Service
public class JwtService {
    // Clave secreta para firmar los tokens, obtenida de la configuración
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    // Tiempo de expiración del token en milisegundos
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     *
     * @param token el token JWT
     * @return el nombre de usuario (subject)
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae una reclamación específica del token JWT.
     *
     * @param token el token JWT
     * @param claimsResolver una función para extraer la reclamación deseada
     * @param <T> el tipo de la reclamación
     * @return el valor de la reclamación extraída
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token JWT para un usuario.
     *
     * @param userDetails los detalles del usuario para el que se generará el token
     * @return el token JWT generado
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token JWT con reclamaciones adicionales.
     *
     * @param extraClaims las reclamaciones adicionales que se agregarán al token
     * @param userDetails los detalles del usuario para el que se generará el token
     * @return el token JWT generado
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    // Construye el token JWT utilizando las reclamaciones proporcionadas.
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Verifica si el token JWT es válido.
     *
     * @param token el token JWT
     * @param userDetails los detalles del usuario
     * @return {@code true} si el token es válido, {@code false} en caso contrario
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Verifica si el token ha expirado.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extrae la fecha de expiración del token JWT.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //  Extrae todas las reclamaciones del token JWT.
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtiene la clave secreta para firmar el token JWT.
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
package com.xpvault.backend.config;

import com.xpvault.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Filtro personalizado que intercepta cada solicitud HTTP para validar el JWT.
 * Si el token es válido, autentica al usuario y lo registra en el contexto de seguridad de Spring.
 */
@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    // Manejador de excepciones para responder adecuadamente ante errores
    private final HandlerExceptionResolver handlerExceptionResolver;
    // Servicio para operaciones relacionadas con JWT (como extracción y validación)
    private final JwtService jwtService;
    // Servicio para cargar los detalles del usuario a partir del email extraído del token
    private final UserDetailsService userDetailsService;

    /**
     * Metodo que intercepta la petición y verifica el token JWT si está presente y es válido.
     *
     * @param request  solicitud HTTP entrante
     * @param response respuesta HTTP saliente
     * @param chain    cadena de filtros para continuar la ejecución
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    )
            throws ServletException, IOException {
        // Ignora la autenticación para rutas públicas como /auth o /users
        if (request.getServletPath().startsWith("/auth") || request.getServletPath().startsWith("/users")) {
            chain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        // Si no hay token o no comienza con "Bearer ", se continúa sin procesar JWT
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // Extrae el token JWT sin el prefijo "Bearer "
            final String jwt = authHeader.substring(7);
            // Extrae el email del usuario del token
            final String userEmail = jwtService.extractUsername(jwt);
            // Obtiene la autenticación actual del contexto de seguridad
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Si hay un email válido y aún no hay usuario autenticado en el contexto
            if (userEmail != null && authentication == null) {
                // Carga los detalles del usuario desde la base de datos
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Verifica si el token es válido para ese usuario
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Crea un token de autenticación con los detalles del usuario
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Agrega información adicional de la petición al token
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Establece el token de autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}

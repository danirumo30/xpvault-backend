package com.xpvault.backend.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración principal de seguridad para la aplicación.
 * Define los filtros, autenticación, autorización y CORS.
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    // Proveedor de autenticación personalizado (DAO + UserDetailsService + PasswordEncoder)
    private final AuthenticationProvider authenticationProvider;
    // Filtro personalizado JWT para autenticar usuarios con tokens
    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Configura la cadena de filtros de seguridad para definir qué rutas son públicas o protegidas,
     * y cómo se maneja la sesión y la autenticación.
     *
     * @param http objeto HttpSecurity para construir la configuración
     * @return SecurityFilterChain resultante
     * @throws Exception si ocurre algún error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // Permite libre acceso a todas las rutas bajo /XX/**
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )
                // Define que no se usarán sesiones (la autenticación es sin estado, vía JWT)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                // Se añade el filtro JWT antes del filtro de autenticación por usuario y contraseña
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura las políticas de CORS para permitir solicitudes desde dominios autorizados.
     *
     * @return CorsConfigurationSource configurado
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Lista de dominios permitidos para acceder a la API
        configuration.setAllowedOrigins(List.of("*"));
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        // Encabezados permitidos en la solicitud
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Fuente de configuración basada en URLs para todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

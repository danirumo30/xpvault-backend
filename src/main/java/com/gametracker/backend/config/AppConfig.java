package com.gametracker.backend.config;

import com.gametracker.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuración principal de seguridad para la autenticación en la aplicación.
 * Define los beans relacionados con la autenticación de usuarios.
 */
@Configuration
@AllArgsConstructor
public class AppConfig {
    private final UserRepository userRepository;

    /**
     * Bean que define el servicio para cargar los detalles del usuario por su nombre de usuario (email).
     *
     * @return una implementación de UserDetailsService
     */
    @Bean
    UserDetailsService userDetailsService() {
        // Busca el usuario por email, lanza excepción si no se encuentra
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Bean que proporciona el codificador de contraseñas usando BCrypt.
     *
     * @return instancia de BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean que expone el AuthenticationManager necesario para la autenticación personalizada.
     *
     * @param config configuración de autenticación proporcionada por Spring
     * @return AuthenticationManager
     * @throws Exception si ocurre un error al obtener el AuthenticationManager
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean que define el proveedor de autenticación que usa el UserDetailsService y el codificador de contraseñas.
     *
     * @return AuthenticationProvider configurado
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}

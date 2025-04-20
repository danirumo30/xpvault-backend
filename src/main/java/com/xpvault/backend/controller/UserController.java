package com.xpvault.backend.controller;

import com.xpvault.backend.entity.AppUser;
import com.xpvault.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST que maneja las operaciones relacionadas con los usuarios.
 * Rutas: /users/me, /users/all
 */
@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Endpoint que devuelve los datos del usuario actualmente autenticado.
     * Se obtiene desde el contexto de seguridad de Spring.
     *
     * @return El usuario autenticado
     */
    @GetMapping("/me")
    public ResponseEntity<AppUser> authenticatedUser() {
        // Se obtiene el contexto de autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Se obtiene el usuario desde los detalles del principal
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    /**
     * Endpoint que devuelve una lista con todos los usuarios del sistema.
     * Requiere autenticación.
     *
     * @return Lista de todos los usuarios registrados
     */
    @PostMapping("/all")
    public ResponseEntity<List<AppUser>> allUsers() {
        List<AppUser> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
}

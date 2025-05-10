package com.xpvault.backend.controller;

import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    /**
     * Endpoint que devuelve los datos del usuario actualmente autenticado.
     * Se obtiene desde el contexto de seguridad de Spring.
     *
     * @return El usuario autenticado
     */
    @GetMapping("/me")
    public ResponseEntity<AppUserModel> authenticatedUser() {
        // Se obtiene el contexto de autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Se obtiene el usuario desde los detalles del principal
        AppUserModel currentUser = (AppUserModel) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    /**
     * Endpoint que devuelve una lista con todos los usuarios del sistema.
     * Requiere autenticación.
     *
     * @return Lista de todos los usuarios registrados
     */
    @PostMapping("/all")
    public ResponseEntity<List<AppUserModel>> allUsers() {
        List<AppUserModel> users = userServiceImpl.allUsers();
        return ResponseEntity.ok(users);
    }
}

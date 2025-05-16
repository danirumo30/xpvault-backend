package com.xpvault.backend.controller;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.facade.UserFacade;
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
    private final UserFacade userFacade;

    /**
     * Endpoint que devuelve los datos del usuario actualmente autenticado.
     * Se obtiene desde el contexto de seguridad de Spring.
     *
     * @return El usuario autenticado
     */
    @GetMapping("/me")
    public ResponseEntity<AppUserDTO> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUserDTO user = userFacade.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    /**
     * Endpoint que devuelve una lista con todos los usuarios del sistema.
     * Requiere autenticación.
     *
     * @return Lista de todos los usuarios registrados
     */
    @PostMapping("/all")
    public ResponseEntity<List<AppUserDTO>> allUsers() {
        List<AppUserDTO> users = userFacade.allUsers();
        return ResponseEntity.ok(users);
    }
}

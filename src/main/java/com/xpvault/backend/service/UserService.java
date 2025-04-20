package com.gametracker.backend.service;

import com.gametracker.backend.entity.AppUser;
import com.gametracker.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los usuarios.
 * Proporciona métodos para acceder a los usuarios almacenados en la base de datos.
 */
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     *
     * @return una lista de usuarios ({@link AppUser})
     */
    public List<AppUser> allUsers() {
        return userRepository.findAll();
    }
}

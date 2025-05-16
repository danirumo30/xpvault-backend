package com.xpvault.backend.service;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.model.AppUserModel;

import java.util.List;

public interface UserService {

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     *
     * @return una lista de usuarios ({@link AppUserDTO})
     */
    List<AppUserModel> allUsers();
    AppUserModel findByUsername(String username);

}

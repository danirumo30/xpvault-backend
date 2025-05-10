package com.xpvault.backend.service;

import com.xpvault.backend.model.AppUserModel;

import java.util.List;

public interface UserService {

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     *
     * @return una lista de usuarios ({@link AppUserModel})
     */
    List<AppUserModel> allUsers();

}

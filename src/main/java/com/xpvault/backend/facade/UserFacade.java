package com.xpvault.backend.facade;

import com.xpvault.backend.dto.AppUserDTO;

import java.util.List;

public interface UserFacade {

    List<AppUserDTO> allUsers();
    AppUserDTO findByUsername(String username);

}

package com.xpvault.backend.facade;

import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserDTO;

import java.util.List;

public interface SteamUserFacade {

    List<OwnedSteamGameDTO> getOwnedGames(Long steamId);
    List<SteamUserDTO> getAllUsers();

}

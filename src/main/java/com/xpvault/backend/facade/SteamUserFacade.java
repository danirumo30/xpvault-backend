package com.xpvault.backend.facade;

import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserTopDTO;

import java.util.List;

public interface SteamUserFacade {

    List<OwnedSteamGameDTO> getOwnedGames(Long steamId);
    List<SteamUserTopDTO> getAllUsers();
    Long getSteamIdByUsername(String username);

}

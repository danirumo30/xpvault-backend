package com.xpvault.backend.facade;

import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.dto.SteamUserTopDTO;

import java.util.List;

public interface SteamUserFacade {

    List<OwnedSteamGameDTO> getOwnedGames(Long steamId);
    List<OwnedSteamGameDTO> getTenOwnedGames(Long steamId);
    List<SteamUserTopDTO> getAllUsers();
    Long getSteamIdByUsername(String username);
    SteamUserDTO getSteamUserById(Long steamId);
    String getUsernameBySteamId(Long steamId);
    SteamUserDTO save(SteamUserDTO steamUserDTO);

}

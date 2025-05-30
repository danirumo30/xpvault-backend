package com.xpvault.backend.facade;

import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.dto.AppUserTopDTO;

import java.util.List;

public interface SteamUserFacade {

    List<OwnedSteamGameDTO> getOwnedGames(Long steamId);
    List<OwnedSteamGameDTO> getTwentyOwnedGames(Long steamId);
    List<AppUserTopDTO> getAllUsers();
    Long getSteamIdByUsername(String username);
    SteamUserDTO getSteamUserById(Long steamId);
    String getUsernameBySteamId(Long steamId);
    SteamUserDTO save(SteamUserDTO steamUserDTO);

}

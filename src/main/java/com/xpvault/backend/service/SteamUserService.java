package com.xpvault.backend.service;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerOwnedGame;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerProfile;
import com.xpvault.backend.model.SteamUserModel;

import java.util.List;

public interface SteamUserService {

    List<SteamPlayerOwnedGame> getOwnedGames(Long steamId);
    Long getSteamIdByUsername(String username);
    String getUsernameBySteamId(Long steamId);
    SteamPlayerProfile getPlayerProfile(Long steamId);
    SteamUserModel save(SteamUserModel steamUserModel);
    Long getTotalTimePlayed(Long steamId);

}

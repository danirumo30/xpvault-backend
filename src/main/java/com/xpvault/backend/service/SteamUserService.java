package com.xpvault.backend.service;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerOwnedGame;

import java.util.List;

public interface SteamUserService {

    List<SteamPlayerOwnedGame> getOwnedGames(Long steamId);

}

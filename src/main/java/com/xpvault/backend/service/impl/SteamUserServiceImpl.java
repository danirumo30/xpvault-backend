package com.xpvault.backend.service.impl;

import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamPlayerService;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerOwnedGame;
import com.xpvault.backend.service.SteamUserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class SteamUserServiceImpl implements SteamUserService {

    private final SteamPlayerService playerService;

    @Override
    public List<SteamPlayerOwnedGame> getOwnedGames(Long steamId) {
        return playerService.getOwnedGames(steamId, true, true)
                            .thenApply(ownedGames -> ownedGames)
                            .join();
    }
}

package com.xpvault.backend.service.impl;

import com.ibasco.agql.protocols.valve.steam.webapi.enums.VanityUrlType;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamPlayerService;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamUser;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerOwnedGame;
import com.xpvault.backend.service.SteamUserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class SteamUserServiceImpl implements SteamUserService {

    private final SteamPlayerService playerService;
    private final SteamUser steamUser;

    @Override
    public List<SteamPlayerOwnedGame> getOwnedGames(Long steamId) {
        return playerService.getOwnedGames(steamId, true, true)
                            .thenApply(ownedGames -> ownedGames)
                            .join();
    }

    @Override
    public Long getSteamIdByUsername(String username) {
        return steamUser.getSteamIdFromVanityUrl(username, VanityUrlType.INDIVIDUAL_PROFILE)
                        .thenApply(steamId -> steamId)
                        .join();
    }
}

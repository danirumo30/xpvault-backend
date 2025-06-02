package com.xpvault.backend.service.impl;

import com.ibasco.agql.protocols.valve.steam.webapi.enums.VanityUrlType;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamPlayerService;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamUser;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerOwnedGame;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerProfile;
import com.xpvault.backend.dao.SteamUserDAO;
import com.xpvault.backend.model.SteamUserModel;
import com.xpvault.backend.service.SteamUserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class SteamUserServiceImpl implements SteamUserService {

    private final SteamPlayerService playerService;
    private final SteamUser steamUser;
    private final SteamUserDAO steamUserDAO;

    @Override
    public List<SteamPlayerOwnedGame> getOwnedGames(Long steamId) {
        List<SteamPlayerOwnedGame> games = playerService.getOwnedGames(steamId, true, true)
                                                        .thenApply(ownedGames -> ownedGames)
                                                        .join();
        return games != null ? games : List.of();
    }

    @Override
    public List<SteamPlayerOwnedGame> getTwentyOwnedGames(Long steamId) {
        List<SteamPlayerOwnedGame> games =  playerService.getOwnedGames(steamId, true, true)
                                                         .thenApply(ownedGames -> ownedGames)
                                                         .join();
        return games != null ? games.subList(0, 20) : List.of();
    }

    @Override
    public Long getSteamIdByUsername(String username) {
        return steamUser.getSteamIdFromVanityUrl(username, VanityUrlType.INDIVIDUAL_PROFILE)
                        .thenApply(steamId -> steamId)
                        .join();
    }

    @Override
    public String getUsernameBySteamId(Long steamId) {
        return steamUser.getPlayerProfile(steamId)
                        .thenApply(user -> user)
                        .join()
                        .getName();
    }

    @Override
    public SteamPlayerProfile getPlayerProfile(Long steamId) {
        return steamUser.getPlayerProfile(steamId)
                        .thenApply(user -> user)
                        .join();
    }

    @Override
    public SteamUserModel save(SteamUserModel steamUserModel) {
        return steamUserDAO.save(steamUserModel);
    }

    @Override
    public Long getTotalTimePlayed(Long steamId) {
        List<SteamPlayerOwnedGame> ownedGames = getOwnedGames(steamId);
        return ownedGames.stream()
                         .mapToLong(SteamPlayerOwnedGame::getTotalPlaytime)
                         .sum();
    }

    @Override
    public Optional<SteamUserModel> findBySteamId(Long steamId) {
        return steamUserDAO.findBySteamId(steamId);
    }
}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
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
    private SteamUserService self;

    @Lazy
    @Autowired
    public void setSelf(SteamUserService self) {
        this.self = self;
    }

    @Cacheable(value = "owned_games", key = "#steamId")
    @Override
    public List<SteamPlayerOwnedGame> getOwnedGames(Long steamId) {
        List<SteamPlayerOwnedGame> games = playerService.getOwnedGames(steamId, true, true)
                                                        .thenApply(ownedGames -> ownedGames)
                                                        .join();
        return games != null ? games : List.of();
    }

    @Cacheable(value = "owned_games_20", key = "#steamId")
    @Override
    public List<SteamPlayerOwnedGame> getTwentyOwnedGames(Long steamId) {
        List<SteamPlayerOwnedGame> games =  playerService.getOwnedGames(steamId, true, true)
                                                         .thenApply(ownedGames -> ownedGames)
                                                         .join();
        return games != null ? games.subList(0, 20) : List.of();
    }

    @Cacheable(value = "steam_id_by_username", key = "#username")
    @Override
    public Long getSteamIdByUsername(String username) {
        return steamUser.getSteamIdFromVanityUrl(username, VanityUrlType.INDIVIDUAL_PROFILE)
                        .thenApply(steamId -> steamId)
                        .join();
    }

    @Cacheable(value = "username_by_steam_id", key = "#steamId")
    @Override
    public String getUsernameBySteamId(Long steamId) {
        return steamUser.getPlayerProfile(steamId)
                        .thenApply(user -> user)
                        .join()
                        .getName();
    }

    @Cacheable(value = "player_profile", key = "#steamId")
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

    @Cacheable(value = "total_time_played", key = "#steamId")
    @Override
    public Long getTotalTimePlayed(Long steamId) {
        List<SteamPlayerOwnedGame> ownedGames = self.getOwnedGames(steamId);
        return ownedGames.stream()
                         .mapToLong(SteamPlayerOwnedGame::getTotalPlaytime)
                         .sum();
    }

    @Override
    public Optional<SteamUserModel> findBySteamId(Long steamId) {
        return steamUserDAO.findBySteamId(steamId);
    }
}

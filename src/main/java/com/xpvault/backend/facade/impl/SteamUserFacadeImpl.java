package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.SteamPlayerOwnedGameToOwnedSteamGameDTOConverter;
import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserTopDTO;
import com.xpvault.backend.facade.SteamUserFacade;
import com.xpvault.backend.service.SteamUserService;
import com.xpvault.backend.service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class SteamUserFacadeImpl implements SteamUserFacade {

    private final UserService userService;
    private final SteamUserService steamUserService;
    private final SteamPlayerOwnedGameToOwnedSteamGameDTOConverter steamPlayerOwnedGameToOwnedSteamGameDTOConverter;

    @Override
    public List<OwnedSteamGameDTO> getOwnedGames(Long steamId) {
        return steamUserService.getOwnedGames(steamId)
                               .stream()
                               .map(steamPlayerOwnedGameToOwnedSteamGameDTOConverter::convert)
                               .toList();
    }

    @Override
    public List<SteamUserTopDTO> getAllUsers() {
        return userService.allUsers()
                   .stream()
                   .filter(user -> user.getSteamId() != null)
                   .map(user -> {
                       List<OwnedSteamGameDTO> ownedGames = getOwnedGames(user.getSteamId());
                       long totalTime = ownedGames.stream()
                                                  .mapToLong(OwnedSteamGameDTO::getTotalTime)
                                                  .sum();

                       return new SteamUserTopDTO(
                               user.getSteamId(),
                               totalTime
                       );
                   })
                   .sorted(Comparator.comparingLong(SteamUserTopDTO::getTotalTimePlayed).reversed())
                   .toList();
    }

    @Override
    public Long getSteamIdByUsername(String username) {
        return steamUserService.getSteamIdByUsername(username);
    }
}

package com.xpvault.backend.facade.impl;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerProfile;
import com.xpvault.backend.converter.SteamPlayerOwnedGameToOwnedSteamGameDTOConverter;
import com.xpvault.backend.converter.SteamPlayerProfileToSteamUserDTOConverter;
import com.xpvault.backend.converter.SteamUserDTOToSteamUserModelConverter;
import com.xpvault.backend.converter.SteamUserModelToSteamUserDTOConverter;
import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.dto.SteamUserTopDTO;
import com.xpvault.backend.facade.SteamUserFacade;
import com.xpvault.backend.facade.UserFacade;
import com.xpvault.backend.model.SteamUserModel;
import com.xpvault.backend.service.SteamUserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class SteamUserFacadeImpl implements SteamUserFacade {

    private final UserFacade userFacade;
    private final SteamUserService steamUserService;
    private final SteamPlayerOwnedGameToOwnedSteamGameDTOConverter steamPlayerOwnedGameToOwnedSteamGameDTOConverter;
    private final SteamPlayerProfileToSteamUserDTOConverter steamPlayerProfileToSteamUserDTOConverter;
    private final SteamUserDTOToSteamUserModelConverter steamUserDTOToSteamUserModelConverter;
    private final SteamUserModelToSteamUserDTOConverter steamUserModelToSteamUserDTOConverter;

    @Override
    public List<OwnedSteamGameDTO> getOwnedGames(Long steamId) {
        return steamUserService.getOwnedGames(steamId)
                               .stream()
                               .map(steamPlayerOwnedGameToOwnedSteamGameDTOConverter::convert)
                               .toList();
    }

    @Override
    public List<SteamUserTopDTO> getAllUsers() {
        return userFacade.allUsers()
                          .stream()
                          .filter(user -> user.getSteamUser() != null)
                          .map(user -> new SteamUserTopDTO(
                                  user.getUsername(),
                                  steamUserService.getTotalTimePlayed(user.getSteamUser().getSteamId())
                                  )
                          )
                          .sorted(Comparator.comparingLong(SteamUserTopDTO::getTotalTimePlayed).reversed())
                          .toList();
    }

    @Override
    public Long getSteamIdByUsername(String username) {
        return steamUserService.getSteamIdByUsername(username);
    }

    @Override
    public SteamUserDTO getSteamUserById(Long steamId) {
        SteamPlayerProfile profile = steamUserService.getPlayerProfile(steamId);

        Long totalTimePlayed = steamUserService.getTotalTimePlayed(steamId);

        List<OwnedSteamGameDTO> ownedSteamGames = getOwnedGames(steamId);

        return steamPlayerProfileToSteamUserDTOConverter.convert(profile, totalTimePlayed, ownedSteamGames);
    }

    @Override
    public String getUsernameBySteamId(Long steamId) {
        return steamUserService.getUsernameBySteamId(steamId);
    }

    @Override
    public SteamUserDTO save(SteamUserDTO steamUserDTO) {
        SteamUserModel steamUserModel = steamUserDTOToSteamUserModelConverter.convert(getSteamUserById(steamUserDTO.getSteamId()));

        SteamUserModel saved = steamUserService.save(steamUserModel);

        return steamUserModelToSteamUserDTOConverter.convert(saved);
    }
}

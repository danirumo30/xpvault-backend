package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDetailsDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.service.SteamUserService;
import com.xpvault.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppUserModelToAppUserDetailsDTOConverter {

    private final UserService userService;
    private final SteamUserService steamUserService;
    private final SteamPlayerProfileToSteamUserDTOConverter steamPlayerProfileToSteamUserDTOConverter;
    private final SteamPlayerOwnedGameToOwnedSteamGameDTOConverter steamPlayerOwnedGameToOwnedSteamGameDTOConverter;

    public AppUserDetailsDTO convert(AppUserModel source, List<TvSerieDTO> tvSeries, List<MovieDTO> movies) {

        SteamUserDTO steamUser = null;
        Long totalTimePlayed = null;
        List<OwnedSteamGameDTO> ownedSteamGames = null;

        if (source.getSteamUser() != null) {
            totalTimePlayed = steamUserService.getTotalTimePlayed(source.getSteamUser().getSteamId());

            ownedSteamGames = steamUserService.getOwnedGames(source.getSteamUser().getSteamId())
                                              .stream()
                                              .map(steamPlayerOwnedGameToOwnedSteamGameDTOConverter::convert)
                                              .toList();

            steamUser = steamPlayerProfileToSteamUserDTOConverter.convert(steamUserService.getPlayerProfile(source.getSteamUser().getSteamId()), totalTimePlayed, ownedSteamGames);
        }

        return new AppUserDetailsDTO(
                source.getId(),
                source.getUsername(),
                steamUser,
                totalTimePlayed,
                userService.getTotalMoviesTime(source),
                userService.getTotalTvSeriesTime(source),
                source.getFriends().size(),
                movies,
                tvSeries,
                ownedSteamGames
        );
    }
}

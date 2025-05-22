package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDetailsDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppUserModelToAppUserDetailsDTOConverter {

    private final UserService userService;

    public AppUserDetailsDTO convert(AppUserModel source, SteamUserDTO steamUser, List<TvSerieDTO> tvSeries, List<MovieDTO> movies) {

        Long totalTimePlayed = null;
        List<OwnedSteamGameDTO> ownedGames = null;

        if (steamUser != null) {
            ownedGames = steamUser.getOwnedGames();
            totalTimePlayed = steamUser.getTotalTimePlayed();
        }

        return new AppUserDetailsDTO(
                source.getId(),
                source.getUsername(),
                steamUser,
                totalTimePlayed,
                userService.getTotalMoviesTime(source),
                userService.getTotalTvSeriesTime(source),
                movies,
                tvSeries,
                ownedGames
        );
    }
}

package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserTopDTO;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.service.SteamUserService;
import com.xpvault.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AppUserModelToAppUserTopDTOConverter implements Converter<AppUserModel, AppUserTopDTO> {

    private final SteamUserService steamUserService;
    private final UserService userService;

    @Override
    public AppUserTopDTO convert(AppUserModel source) {

        Long steamId = source.getSteamUser() != null ? source.getSteamUser().getSteamId() : null;

        Integer totalTimePlayed = (steamId != null)
                ? steamUserService.getTotalTimePlayed(steamId).intValue()
                : 0;

        Integer totalTimeMovies = source.getMovies() != null
                ? userService.getTotalMoviesTime(source)
                : 0;

        Integer totalTimeTvSeries = source.getTvSeries() != null
                ? userService.getTotalTvSeriesTime(source)
                : 0;

        return new AppUserTopDTO(
                source.getId(),
                source.getUsername(),
                source.getProfileImage() != null
                        ? Base64.getEncoder().encodeToString(source.getProfileImage())
                        : null,
                (long) (totalTimePlayed + totalTimeMovies + totalTimeTvSeries)
        );
    }
}

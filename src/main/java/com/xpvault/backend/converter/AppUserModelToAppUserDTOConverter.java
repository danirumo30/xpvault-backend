package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.service.SteamUserService;
import com.xpvault.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AppUserModelToAppUserDTOConverter implements Converter<AppUserModel, AppUserDTO> {

    private final SteamUserModelToSteamUserDTOConverter steamUserModelToSteamUserDTOConverter;
    private final SteamUserService steamUserService;
    private final UserService userService;

    @Override
    public AppUserDTO convert(AppUserModel source) {
        SteamUserDTO steamUserDTO = source.getSteamUser() != null
                ? steamUserModelToSteamUserDTOConverter.convert(source.getSteamUser())
                : null;

        Long steamId = steamUserDTO != null ? steamUserDTO.getSteamId() : null;

        Integer totalTimePlayed = (steamId != null)
                ? steamUserService.getTotalTimePlayed(steamId).intValue()
                : null;

        Integer totalGames = (steamId != null)
                ? steamUserService.getOwnedGames(steamId).size()
                : null;

        Integer totalTimeMovies = source.getMovies() != null
                ? userService.getTotalMoviesTime(source)
                : null;

        Integer totalTimeTvSeries = source.getTvSeries() != null
                ? userService.getTotalTvSeriesTime(source)
                : null;

        Integer totalMovies = source.getMovies() != null
                ? source.getMovies().size()
                : null;

        Integer totalTvSeries = source.getTvSeries() != null
                ? source.getTvSeries().size()
                : null;

        return new AppUserDTO(
                source.getId(),
                source.getProfileImage() != null
                        ? Base64.getEncoder().encodeToString(source.getProfileImage())
                        : null,
                source.getUsername(),
                source.getEmail(),
                source.getPassword(),
                source.getVerificationCode(),
                source.getVerificationExpiration(),
                steamUserDTO,
                totalTimePlayed,
                totalTimeMovies,
                totalTimeTvSeries,
                totalGames,
                totalMovies,
                totalTvSeries
        );
    }
}

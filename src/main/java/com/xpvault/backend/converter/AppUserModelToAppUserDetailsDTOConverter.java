package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDetailsDTO;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.model.MovieModel;
import com.xpvault.backend.model.TvSerieModel;
import com.xpvault.backend.service.SteamUserService;
import com.xpvault.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AppUserModelToAppUserDetailsDTOConverter implements Converter<AppUserModel, AppUserDetailsDTO> {

    private final SteamUserModelToSteamUserDTOConverter steamUserModelToSteamUserDTOConverter;
    private final MovieModelToMovieDTOConverter movieModelToMovieDTOConverter;
    private final TvSerieModelToTvSerieDTOConverter tvSerieModelToTvSerieDTOConverter;
    private final SteamPlayerOwnedGameToOwnedSteamGameDTOConverter steamPlayerOwnedGameToOwnedSteamGameDTOConverter;
    private final UserService userService;
    private final SteamUserService steamUserService;

    @Override
    public AppUserDetailsDTO convert(AppUserModel source) {

        Set<MovieModel> movies = source.getMovies() == null ? null : source.getMovies();
        Set<TvSerieModel> tvSeries = source.getTvSeries() == null ? null : source.getTvSeries();

        return new AppUserDetailsDTO(
                source.getId(),
                source.getUsername(),
                steamUserModelToSteamUserDTOConverter.convert(source.getSteamUser()),
                steamUserService.getTotalTimePlayed(source.getSteamUser().getSteamId()),
                userService.getTotalMoviesTime(source),
                userService.getTotalTvSeriesTime(source),
                movies == null ? null : movies.stream()
                                              .map(movieModelToMovieDTOConverter::convert)
                                              .toList(),
                tvSeries == null ? null : tvSeries.stream()
                                                  .map(tvSerieModelToTvSerieDTOConverter::convert)
                                                  .toList(),
                steamUserService.getOwnedGames(source.getSteamUser().getSteamId())
                                .stream()
                                .map(steamPlayerOwnedGameToOwnedSteamGameDTOConverter::convert)
                                .toList()
        );
    }
}

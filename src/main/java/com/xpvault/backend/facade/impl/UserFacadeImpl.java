package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.AppUserModelToAppUserDTOConverter;
import com.xpvault.backend.converter.AppUserModelToAppUserDetailsDTOConverter;
import com.xpvault.backend.converter.MovieModelToMovieDTOConverter;
import com.xpvault.backend.converter.TvSerieModelToTvSerieDTOConverter;
import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.dto.AppUserDetailsDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.facade.UserFacade;
import com.xpvault.backend.service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final AppUserModelToAppUserDTOConverter appUserModelToAppUserDTOConverter;
    private final AppUserModelToAppUserDetailsDTOConverter appUserModelToAppUserDetailsDTOConverter;
    private final MovieModelToMovieDTOConverter movieModelToMovieDTOConverter;
    private final TvSerieModelToTvSerieDTOConverter tvSerieModelToTvSerieDTOConverter;

    @Override
    public List<AppUserDTO> allUsers() {
        return userService.allUsers()
                          .stream()
                          .map(appUserModelToAppUserDTOConverter::convert)
                          .toList();
    }

    @Override
    public AppUserDTO findByUsername(String username) {
        return appUserModelToAppUserDTOConverter.convert(userService.findByUsername(username));
    }

    @Override
    public List<AppUserDTO> getAllUsersTopTimeMovies() {
        return userService.allUsers()
                          .stream()
                          .map(appUserModelToAppUserDTOConverter::convert)
                          .filter(Objects::nonNull)
                          .filter(user -> user.getTotalTimeMoviesWatched() != null)
                          .sorted(Comparator.comparingLong(AppUserDTO::getTotalTimeMoviesWatched).reversed())
                          .toList();
    }

    @Override
    public List<AppUserDTO> getAllUsersTopTimeTvSeries() {
        return userService.allUsers()
                          .stream()
                          .map(appUserModelToAppUserDTOConverter::convert)
                          .filter(Objects::nonNull)
                          .filter(user -> user.getTotalTimeEpisodesWatched() != null)
                          .sorted(Comparator.comparingLong(AppUserDTO::getTotalTimeEpisodesWatched).reversed())
                          .toList();
    }

    @Override
    public void addMovieToUser(String username, Integer movieId, String language) {
        userService.addMovieToUser(username, movieId, language);
    }

    @Override
    public void addTvSerieToUser(String username, Integer tvSerieId, String language) {
        userService.addTvSerieToUser(username, tvSerieId, language);
    }

    @Override
    public AppUserDetailsDTO findFullUserDetails(String username) {
        return appUserModelToAppUserDetailsDTOConverter.convert(userService.findByUsername(username));
    }

    @Override
    public List<MovieDTO> getMovies(String username) {
        return userService.findMoviesByUsername(username)
                          .stream()
                          .map(movieModelToMovieDTOConverter::convert)
                          .toList();
    }

    @Override
    public List<TvSerieDTO> getTvSeries(String username) {
        return userService.findTvSeriesByUsername(username)
                          .stream()
                          .map(tvSerieModelToTvSerieDTOConverter::convert)
                          .toList();
    }
}

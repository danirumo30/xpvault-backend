package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.AppUserDTOToAppUserModelConverter;
import com.xpvault.backend.converter.AppUserModelToAppUserDTOConverter;
import com.xpvault.backend.converter.AppUserModelToAppUserDetailsDTOConverter;
import com.xpvault.backend.converter.AppUserModelToAppUserTopDTOConverter;
import com.xpvault.backend.converter.MovieModelToMovieDTOConverter;
import com.xpvault.backend.converter.TvSerieModelToTvSerieDTOConverter;
import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.dto.AppUserDetailsDTO;
import com.xpvault.backend.dto.AppUserTopDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.literals.enums.AddResultEnum;
import com.xpvault.backend.facade.MovieFacade;
import com.xpvault.backend.facade.TvSerieFacade;
import com.xpvault.backend.facade.UserFacade;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final MovieFacade movieFacade;
    private final TvSerieFacade tvSerieFacade;
    private final AppUserModelToAppUserTopDTOConverter appUserModelToAppUserTopDTOConverter;
    private final AppUserModelToAppUserDTOConverter appUserModelToAppUserDTOConverter;
    private final AppUserModelToAppUserDetailsDTOConverter appUserModelToAppUserDetailsDTOConverter;
    private final MovieModelToMovieDTOConverter movieModelToMovieDTOConverter;
    private final TvSerieModelToTvSerieDTOConverter tvSerieModelToTvSerieDTOConverter;
    private final AppUserDTOToAppUserModelConverter appUserDTOToAppUserModelConverter;

    @Override
    public List<AppUserTopDTO> allUsersBasic() {
        return userService.allUsers()
                          .stream()
                          .map(appUserModelToAppUserTopDTOConverter::convert)
                          .toList();
    }

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
    public List<AppUserTopDTO> getAllUsersTopMovies() {
        return allUsers().stream()
                         .map(user -> new AppUserTopDTO(
                                  user.getId(),
                                  user.getUsername(),
                                  user.getProfilePhoto(),
                                  user.getTotalTimeMoviesWatched().longValue()
                             )
                         )
                         .filter(user -> user.getTotalTime() != null)
                         .sorted(Comparator.comparingLong(AppUserTopDTO::getTotalTime).reversed())
                         .toList();
    }

    @Override
    public List<AppUserTopDTO> getAllUsersTopTvSeries() {
        return allUsers().stream()
                         .map(user -> new AppUserTopDTO(
                                        user.getId(),
                                        user.getUsername(),
                                        user.getProfilePhoto(),
                                        user.getTotalTimeEpisodesWatched().longValue()
                                )
                         )
                         .filter(user -> user.getTotalTime() != null)
                         .sorted(Comparator.comparingLong(AppUserTopDTO::getTotalTime).reversed())
                         .toList();
    }

    @Override
    public AddResultEnum addMovieToUser(String username, Integer movieId, String language) {
        return userService.addMovieToUser(username, movieId, language);
    }

    @Override
    public AddResultEnum addTvSerieToUser(String username, Integer tvSerieId, String language) {
        return userService.addTvSerieToUser(username, tvSerieId, language);
    }

    @Override
    public AppUserDetailsDTO findFullUserDetails(String username) {
        AppUserModel appUser = userService.findByUsername(username);
        List<TvSerieDTO> tvSeries = appUser.getTvSeries()
                                           .stream()
                                           .map(tvSerieModel ->
                                                   tvSerieFacade.getTvSerieDetailsById(tvSerieModel.getTmdbId(), "en")
                                           )
                                          .toList();

        List<MovieDTO> movies = appUser.getMovies()
                                       .stream()
                                       .map(movieModel ->
                                            movieFacade.getMovieDetailsById(movieModel.getTmdbId(), "en")
                                       )
                                       .toList();

        return appUserModelToAppUserDetailsDTOConverter.convert(appUser, tvSeries, movies);
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

    @Override
    public List<AppUserTopDTO> getFriends(String username) {
        return userService.findFriendsByUsername(username)
                          .stream()
                          .map(appUserModelToAppUserTopDTOConverter::convert)
                          .toList();
    }

    @Override
    public AddResultEnum addFriendToUser(String username, String friendUsername) {
        return userService.addFriendToUser(username, friendUsername);
    }

    @Override
    public List<AppUserDTO> findByUsernameContainsIgnoreCase(String username) {
        return userService.findByUsernameContainsIgnoreCase(username)
                          .stream()
                          .map(appUserModelToAppUserDTOConverter::convert)
                          .toList();
    }

    @Override
    public AppUserDTO save(AppUserDTO appUserDTO) {
        AppUserModel appUserModel = appUserDTOToAppUserModelConverter.convert(appUserDTO);

        Optional.ofNullable(appUserModel)
                .map(AppUserModel::getSteamUser)
                .ifPresent(steamUser -> steamUser.setAppUser(appUserModel));

        AppUserModel saved = userService.save(appUserModel);

        return appUserModelToAppUserDTOConverter.convert(saved);
    }

    @Override
    public AddResultEnum deleteFriendFromUser(String username, String friendUsername) {
        return userService.deleteFriendFromUser(username, friendUsername);
    }

    @Override
    public boolean isFriend(String username, String friendUsername) {
        return userService.isFriend(username, friendUsername);
    }
}

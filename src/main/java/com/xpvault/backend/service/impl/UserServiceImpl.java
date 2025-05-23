package com.xpvault.backend.service.impl;

import com.xpvault.backend.dao.UserDAO;
import com.xpvault.backend.literals.enums.AddResultEnum;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.model.MovieModel;
import com.xpvault.backend.model.TvSerieModel;
import com.xpvault.backend.service.MovieService;
import com.xpvault.backend.service.TvSerieService;
import com.xpvault.backend.service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final MovieService movieService;
    private final TvSerieService tvSerieService;

    @Override
    public List<AppUserModel> allUsers() {
        return userDAO.findAll();
    }

    @Override
    public AppUserModel findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public AddResultEnum addMovieToUser(String username, Integer movieId, String language) {
        return Optional.ofNullable(findByUsername(username))
                .map(user ->
                        Optional.ofNullable(movieService.getMovieDetails(movieId, language))
                                .map(movieDb ->
                                        MovieModel.builder()
                                                  .id(movieService.findByTmdbId(movieId) == null ? null : movieService.findByTmdbId(movieId).getId())
                                                  .title(movieDb.getTitle())
                                                  .description(movieDb.getOverview())
                                                  .tmdbId(movieId)
                                                  .runtime(movieDb.getRuntime())
                                                  .build()
                                )
                                .map(movie -> {
                                    if (user.getMovies() == null) {
                                        user.setMovies(new HashSet<>());
                                    }
                                    if (user.getMovies().contains(movie)) {
                                        return AddResultEnum.ALREADY_EXISTS;
                                    }
                                    user.getMovies().add(movie);
                                    userDAO.save(user);
                                    return AddResultEnum.SUCCESS;
                                })
                                .orElse(AddResultEnum.MOVIE_NOT_FOUND)
                )
                .orElse(AddResultEnum.USER_NOT_FOUND);
    }

    @Override
    public AddResultEnum addTvSerieToUser(String username, Integer tvSerieId, String language) {
        return Optional.ofNullable(findByUsername(username))
                .map(user ->
                        Optional.ofNullable(tvSerieService.getTvSerieDetails(tvSerieId, language))
                                .map(tvSeriesDb -> TvSerieModel.builder()
                                        .id(tvSerieService.findByTmdbId(tvSerieId) == null ? null : tvSerieService.findByTmdbId(tvSerieId).getId())
                                        .title(tvSeriesDb.getName())
                                        .description(tvSeriesDb.getOverview())
                                        .tmdbId(tvSerieId)
                                        .runtime(tvSerieService.getTotalTvSerieTime(tvSeriesDb))
                                        .build())
                                .map(tvSerie -> {
                                    if (user.getTvSeries() == null) {
                                        user.setTvSeries(new HashSet<>());
                                    }
                                    if (user.getTvSeries().contains(tvSerie)) {
                                        return AddResultEnum.ALREADY_EXISTS;
                                    }
                                    user.getTvSeries().add(tvSerie);
                                    userDAO.save(user);
                                    return AddResultEnum.SUCCESS;
                                })
                                .orElse(AddResultEnum.TV_SERIE_NOT_FOUND)
                )
                .orElse(AddResultEnum.USER_NOT_FOUND);
    }

    @Override
    public Integer getTotalMoviesTime(AppUserModel appUserModel) {
        return Optional.ofNullable(appUserModel.getMovies())
                       .stream()
                       .flatMap(Set::stream)
                       .filter(movie -> movie.getRuntime() != null)
                       .mapToInt(MovieModel::getRuntime)
                       .sum();
    }

    @Override
    public Integer getTotalTvSeriesTime(AppUserModel appUserModel) {
        return Optional.ofNullable(appUserModel.getTvSeries())
                       .stream()
                       .flatMap(Set::stream)
                       .filter(tvSerie -> tvSerie.getRuntime() != null)
                       .mapToInt(TvSerieModel::getRuntime)
                       .sum();
    }

    @Override
    public List<MovieModel> findMoviesByUsername(String username) {
        return userDAO.findMoviesByUsername(username);
    }

    @Override
    public List<TvSerieModel> findTvSeriesByUsername(String username) {
        return userDAO.findTvSeriesByUsername(username);
    }

    @Override
    public List<AppUserModel> findByUsernameContainsIgnoreCase(String username) {
        return userDAO.findByUsernameContainsIgnoreCase(username);
    }

    @Override
    public AddResultEnum addFriendToUser(String username, String friendUsername) {
        return Optional.ofNullable(findByUsername(username))
                .map(user ->
                        Optional.ofNullable(findByUsername(friendUsername))
                                .map(friend -> {
                                    if (user.getFriends() == null) {
                                        user.setFriends(new HashSet<>());
                                    }
                                    if (user.getFriends().contains(friend)) {
                                        return AddResultEnum.ALREADY_EXISTS;
                                    }
                                    user.getFriends().add(friend);
                                    if (friend.getFriends() == null) {
                                        friend.setFriends(new HashSet<>());
                                    }
                                    friend.getFriends().add(user);

                                    userDAO.save(user);
                                    userDAO.save(friend);

                                    return AddResultEnum.SUCCESS;
                                })
                                .orElse(AddResultEnum.FRIEND_NOT_FOUND)
                )
                .orElse(AddResultEnum.USER_NOT_FOUND);
    }


    @Override
    public AppUserModel save(AppUserModel appUserModel) {
        return userDAO.save(appUserModel);
    }
}

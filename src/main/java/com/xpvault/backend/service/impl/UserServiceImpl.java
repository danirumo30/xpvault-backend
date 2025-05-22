package com.xpvault.backend.service.impl;

import com.xpvault.backend.dao.UserDAO;
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
    public void addMovieToUser(String username, Integer movieId, String language) {
        Optional.ofNullable(findByUsername(username))
                .ifPresentOrElse(user ->
                        Optional.ofNullable(movieService.getMovieDetails(movieId, language))
                                .map(movieDb -> MovieModel.builder()
                                                          .title(movieDb.getTitle())
                                                          .description(movieDb.getOverview())
                                                          .tmdbId(movieId)
                                                          .runtime(movieDb.getRuntime())
                                                          .build())
                                .ifPresent(movie -> {
                                    if (user.getMovies() == null) {
                                        user.setMovies(new HashSet<>());
                                    }
                                    if (!user.getMovies().contains(movie)) {
                                        user.getMovies().add(movie);
                                        userDAO.save(user);
                                    }
                                }),
                        () -> {
                            throw new IllegalArgumentException("User not found with username: " + username);
                        }
                );
    }

    @Override
    public void addTvSerieToUser(String username, Integer tvSerieId, String language) {
        Optional.ofNullable(findByUsername(username))
                .ifPresentOrElse(user ->
                        Optional.ofNullable(tvSerieService.getTvSerieDetails(tvSerieId, language))
                                .map(tvSeriesDb -> TvSerieModel.builder()
                                                                 .id(tvSerieService.findByTmdbId(tvSerieId) == null ? null : tvSerieService.findByTmdbId(tvSerieId).getId())
                                                                 .title(tvSeriesDb.getName())
                                                                 .description(tvSeriesDb.getOverview())
                                                                 .tmdbId(tvSerieId)
                                                                 .runtime(tvSerieService.getTotalTvSerieTime(tvSeriesDb))
                                                                 .build())
                                .ifPresent(tvSerie -> {
                                    if (user.getTvSeries() == null) {
                                        user.setTvSeries(new HashSet<>());
                                    }
                                    if (!user.getTvSeries().contains(tvSerie)) {
                                        user.getTvSeries().add(tvSerie);
                                        userDAO.save(user);
                                    }
                                }),
                        () -> {
                            throw new IllegalArgumentException("User not found with username: " + username);
                        }
                );
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
    public void addFriendToUser(String username, String friendUsername) {
        Optional.ofNullable(findByUsername(username))
                .ifPresentOrElse(user ->
                                Optional.ofNullable(findByUsername(friendUsername))
                                        .ifPresent(friend -> {
                                            if (user.getFriends() == null) {
                                                user.setFriends(new HashSet<>());
                                            }
                                            if (!user.getFriends().contains(friend)) {
                                                user.getFriends().add(friend);
                                                if (friend.getFriends() == null) {
                                                    friend.setFriends(new HashSet<>());
                                                }
                                                friend.getFriends().add(user);
                                                userDAO.save(user);
                                                userDAO.save(friend);
                                            }
                                        }),
                                () -> {
                                    throw new IllegalArgumentException("User not found with username: " + username);
                                }
                );
    }
}

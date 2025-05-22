package com.xpvault.backend.service;

import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.model.MovieModel;
import com.xpvault.backend.model.TvSerieModel;

import java.util.List;

public interface UserService {

    List<AppUserModel> allUsers();
    AppUserModel findByUsername(String username);
    void addMovieToUser(String username, Integer movieId, String language);
    void addTvSerieToUser(String username, Integer tvSerieId, String language);
    Integer getTotalMoviesTime(AppUserModel appUserModel);
    Integer getTotalTvSeriesTime(AppUserModel appUserModel);
    List<MovieModel> findMoviesByUsername(String username);
    List<TvSerieModel> findTvSeriesByUsername(String username);
    List<AppUserModel> findByUsernameContainsIgnoreCase(String username);
    void addFriendToUser(String username, String friendUsername);

}

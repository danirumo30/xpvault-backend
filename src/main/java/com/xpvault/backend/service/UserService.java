package com.xpvault.backend.service;

import com.xpvault.backend.literals.enums.AddFriendResultEnum;
import com.xpvault.backend.literals.enums.AddMediaResultEnum;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.model.MovieModel;
import com.xpvault.backend.model.TvSerieModel;

import java.util.List;

public interface UserService {

    List<AppUserModel> allUsers();
    AppUserModel findByUsername(String username);
    AddMediaResultEnum addMovieToUser(String username, Integer movieId, String language);
    AddMediaResultEnum addTvSerieToUser(String username, Integer tvSerieId, String language);
    Integer getTotalMoviesTime(AppUserModel appUserModel);
    Integer getTotalTvSeriesTime(AppUserModel appUserModel);
    List<MovieModel> findMoviesByUsername(String username);
    List<TvSerieModel> findTvSeriesByUsername(String username);
    List<AppUserModel> findByUsernameContainsIgnoreCase(String username);
    AddFriendResultEnum addFriendToUser(String username, String friendUsername);
    AppUserModel save(AppUserModel appUserModel);

}

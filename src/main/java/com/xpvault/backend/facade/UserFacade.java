package com.xpvault.backend.facade;

import com.xpvault.backend.dto.AppUserDTO;

import java.util.List;

public interface UserFacade {

    List<AppUserDTO> allUsers();
    AppUserDTO findByUsername(String username);
    List<AppUserDTO> getAllUsersTopTimeMovies();
    List<AppUserDTO> getAllUsersTopTimeTvSeries();
    void addMovieToUser(String username, Integer movieId, String language);
    void addTvSerieToUser(String username, Integer tvSerieId, String language);

}

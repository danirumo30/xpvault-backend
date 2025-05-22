package com.xpvault.backend.facade;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.dto.AppUserDetailsDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.dto.TvSerieDTO;

import java.util.List;

public interface UserFacade {

    List<AppUserDTO> allUsers();
    AppUserDTO findByUsername(String username);
    List<AppUserDTO> getAllUsersTopMovies();
    List<AppUserDTO> getAllUsersTopTvSeries();
    void addMovieToUser(String username, Integer movieId, String language);
    void addTvSerieToUser(String username, Integer tvSerieId, String language);
    AppUserDetailsDTO findFullUserDetails(String username);
    List<MovieDTO> getMovies(String username);
    List<TvSerieDTO> getTvSeries(String username);
    void addFriendToUser(String username, String friendUsername);
    List<AppUserDTO> findByUsernameContainsIgnoreCase(String username);
    AppUserDTO save(AppUserDTO appUserDTO);

}

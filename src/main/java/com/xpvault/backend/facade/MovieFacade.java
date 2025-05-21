package com.xpvault.backend.facade;

import com.xpvault.backend.dto.MovieDTO;

import java.util.List;

public interface MovieFacade {

    List<MovieDTO> getPopularMovies(String language, int page, String region);
    List<MovieDTO> getTopRatedMovies(String language, int page, String region);
    List<MovieDTO> getUpcomingMovies(String language, int page, String region);
    List<MovieDTO> getMovieByTitle(String title, String language, int page, String region);
    List<MovieDTO> getMovieByGenre(String genre, String language, int page);
    MovieDTO getMovieDetailsById(int id, String language);

}

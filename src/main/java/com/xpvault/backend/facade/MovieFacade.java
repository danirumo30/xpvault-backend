package com.xpvault.backend.facade;

import com.xpvault.backend.dto.BasicCastDTO;
import com.xpvault.backend.dto.BasicDirectorDTO;
import com.xpvault.backend.dto.BasicMovieDTO;
import com.xpvault.backend.dto.MovieDTO;
import info.movito.themoviedbapi.model.movies.MovieDb;

import java.util.List;
import java.util.Optional;

public interface MovieFacade {

    List<BasicMovieDTO> getPopularMovies(String language, int page, String region);
    List<BasicMovieDTO> getTopRatedMovies(String language, int page, String region);
    List<BasicMovieDTO> getUpcomingMovies(String language, int page, String region);
    List<BasicMovieDTO> getMovieByTitle(String title, String language, int page, String region);
    List<BasicMovieDTO> getMovieByGenre(String genre, String language, int page);
    MovieDTO getMovieDetailsById(int id, String language);
    MovieDb getMovieFullDetailsById(int id, String language);
    Optional<BasicDirectorDTO> getBasicDirectorDTO(MovieDb source);
    List<BasicCastDTO> getBasicCastDTO(MovieDb source);

}

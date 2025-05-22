package com.xpvault.backend.service;

import com.xpvault.backend.model.MovieModel;
import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.Crew;
import info.movito.themoviedbapi.model.movies.MovieDb;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<MovieDb> getPopularMovies(String language, int page, String region);
    List<MovieDb> getTopRatedMovies(String language, int page, String region);
    List<MovieDb> getUpcomingMovies(String language, int page, String region);
    List<MovieDb> getMovieByTitle(String title, String language, int page, String region);
    List<MovieDb> getMovieByGenre(String genre, String language, int page);
    MovieDb getMovieDetails(int movieId, String language);
    Credits getMovieCredits(int movieId, String language);
    MovieModel findByTmdbId(Integer movieId);
    List<String> getMovieGenres(MovieDb source);
    Optional<Crew> getDirector(MovieDb source);
    List<Cast> getCasting(MovieDb source);

}

package com.xpvault.backend.service;

import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.MovieDb;

import java.util.List;

public interface MovieService {

    List<MovieDb> getPopularMovies(String language, int page, String region);
    List<MovieDb> getTopRatedMovies(String language, int page, String region);
    List<MovieDb> getUpcomingMovies(String language, int page, String region);
    List<MovieDb> getMovieByTitle(String title, String language, int page, String region);
    List<MovieDb> getMovieByGenre(String genre, String language, int page);
    MovieDb getMovieDetails(int movieId, String language);
    Credits getMovieCredits(int movieId, String language);

}

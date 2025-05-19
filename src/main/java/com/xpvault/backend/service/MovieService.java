package com.xpvault.backend.service;

import info.movito.themoviedbapi.model.movies.MovieDb;

import java.util.List;

public interface MovieService {

    List<MovieDb> getPopularMovies(String language, int page, String region);

}

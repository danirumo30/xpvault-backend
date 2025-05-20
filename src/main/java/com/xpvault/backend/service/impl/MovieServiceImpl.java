package com.xpvault.backend.service.impl;

import com.xpvault.backend.service.MovieService;
import info.movito.themoviedbapi.TmdbMovieLists;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.MovieDb;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class MovieServiceImpl implements MovieService {

    private final TmdbMovies tmdbMovies;
    private final TmdbMovieLists tmdbMoviesList;
    private final TmdbSearch tmdbSearch;

    @SneakyThrows
    @Override
    public List<MovieDb> getPopularMovies(String language, int page, String region) {
        return tmdbMoviesList.getPopular(language, page, region)
                             .getResults()
                             .stream()
                             .map(movie-> {
                                 MovieDb movieDb = getMovieDetails(movie.getId(), language);
                                 movieDb.setCredits(getMovieCredits(movie.getId(), language));
                                 return movieDb;
                             })
                             .toList();
    }

    @SneakyThrows
    @Override
    public List<MovieDb> getTopRatedMovies(String language, int page, String region) {
        return tmdbMoviesList.getTopRated(language, page, region)
                             .getResults()
                             .stream()
                             .map(movie-> {
                                    MovieDb movieDb = getMovieDetails(movie.getId(), language);
                                    movieDb.setCredits(getMovieCredits(movie.getId(), language));
                                    return movieDb;
                             })
                             .toList();
    }

    @SneakyThrows
    @Override
    public List<MovieDb> getUpcomingMovies(String language, int page, String region) {
        return tmdbMoviesList.getUpcoming(language, page, region)
                             .getResults()
                             .stream()
                             .map(movie-> {
                                    MovieDb movieDb = getMovieDetails(movie.getId(), language);
                                    movieDb.setCredits(getMovieCredits(movie.getId(), language));
                                    return movieDb;
                             })
                             .toList();
    }

    @SneakyThrows
    @Override
    public List<MovieDb> getMovieByTitle(String title, String language, int page, String region) {
        return tmdbSearch.searchMovie(title, false, language, null, page, region, null)
                         .getResults()
                         .stream()
                         .map(movie-> {
                                MovieDb movieDb = getMovieDetails(movie.getId(), language);
                                movieDb.setCredits(getMovieCredits(movie.getId(), language));
                                return movieDb;
                         })
                         .toList();
    }

    @SneakyThrows
    private MovieDb getMovieDetails(int movieId, String language) {
        return tmdbMovies.getDetails(movieId, language);
    }

    @SneakyThrows
    private Credits getMovieCredits(int movieId, String language) {
        return tmdbMovies.getCredits(movieId, language);
    }
}

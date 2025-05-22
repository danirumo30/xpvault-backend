package com.xpvault.backend.service.impl;

import com.xpvault.backend.dao.MovieDAO;
import com.xpvault.backend.model.MovieModel;
import com.xpvault.backend.service.MovieService;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbGenre;
import info.movito.themoviedbapi.TmdbMovieLists;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.core.IdElement;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.builders.discover.DiscoverMovieParamBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;
    private final TmdbMovies tmdbMovies;
    private final TmdbMovieLists tmdbMoviesList;
    private final TmdbSearch tmdbSearch;
    private final TmdbDiscover tmdbDiscover;
    private final TmdbGenre tmdbGenre;

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
    @Override
    public List<MovieDb> getMovieByGenre(String genre, String language, int page) {
        Optional<Integer> genreId = tmdbGenre.getMovieList(language)
                                             .stream()
                                             .filter(g -> g.getName().equalsIgnoreCase(genre))
                                             .map(IdElement::getId)
                                             .findFirst();

        DiscoverMovieParamBuilder builder = new DiscoverMovieParamBuilder().withOriginalLanguage(language)
                                                                           .page(page)
                                                                           .withGenres(
                                                                                   List.of(genreId.orElse(0)),
                                                                                   false
                                                                           );

        return tmdbDiscover.getMovie(builder)
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
    public MovieDb getMovieDetails(int movieId, String language) {
        return tmdbMovies.getDetails(movieId, language);
    }

    @SneakyThrows
    public Credits getMovieCredits(int movieId, String language) {
        return tmdbMovies.getCredits(movieId, language);
    }

    @Override
    public MovieModel findByTmdbId(Integer movieId) {
        return movieDAO.findByTmdbId(movieId);
    }

    @Override
    public List<String> getMovieGenres(MovieDb source) {
        return source.getGenres()
                     .stream()
                     .map(Genre::getName)
                     .toList();
    }
}

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
import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.Crew;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.builders.discover.DiscoverMovieParamBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    private MovieService self;

    @Lazy
    @Autowired
    public void setSelf(MovieService self) {
        this.self = self;
    }

    @SneakyThrows
    @Cacheable(value = "popular_movies", key = "#language + '_' + #page + '_' + #region")
    @Override
    public List<MovieDb> getPopularMovies(String language, int page, String region) {
        return tmdbMoviesList.getPopular(language, page, region)
                             .getResults()
                             .stream()
                             .map(movie-> {
                                 MovieDb movieDb = self.getMovieDetails(movie.getId(), language);
                                 movieDb.setCredits(self.getMovieCredits(movie.getId(), language));
                                 return movieDb;
                             })
                             .toList();
    }

    @SneakyThrows
    @Cacheable(value = "top_rated_movies", key = "'toprated_' + #language + '_' + #page + '_' + #region")
    @Override
    public List<MovieDb> getTopRatedMovies(String language, int page, String region) {
        return tmdbMoviesList.getTopRated(language, page, region)
                             .getResults()
                             .stream()
                             .map(movie-> {
                                    MovieDb movieDb = self.getMovieDetails(movie.getId(), language);
                                    movieDb.setCredits(self.getMovieCredits(movie.getId(), language));
                                    return movieDb;
                             })
                             .toList();
    }

    @SneakyThrows
    @Cacheable(value = "upcoming_movies", key = "'upcoming_' + #language + '_' + #page + '_' + #region")
    @Override
    public List<MovieDb> getUpcomingMovies(String language, int page, String region) {
        return tmdbMoviesList.getUpcoming(language, page, region)
                             .getResults()
                             .stream()
                             .map(movie-> {
                                    MovieDb movieDb = self.getMovieDetails(movie.getId(), language);
                                    movieDb.setCredits(self.getMovieCredits(movie.getId(), language));
                                    return movieDb;
                             })
                             .toList();
    }

    @SneakyThrows
    @Cacheable(value = "movies_by_title", key = "#title + '_' + #language + '_' + #page + '_' + #region")
    @Override
    public List<MovieDb> getMovieByTitle(String title, String language, int page, String region) {
        return tmdbSearch.searchMovie(title, false, language, null, page, region, null)
                         .getResults()
                         .stream()
                         .map(movie-> {
                                MovieDb movieDb = self.getMovieDetails(movie.getId(), language);
                                if (movieDb != null) {
                                    movieDb.setCredits(self.getMovieCredits(movie.getId(), language));
                                    return movieDb;
                                }
                                return null;
                         })
                         .filter(Objects::nonNull)
                         .toList();
    }

    @SneakyThrows
    @Cacheable(value = "movies_by_genre", key = "#language + '_' + #page + '_' + #genre")
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
                                MovieDb movieDb = self.getMovieDetails(movie.getId(), language);
                                movieDb.setCredits(self.getMovieCredits(movie.getId(), language));
                                return movieDb;
                           })
                           .toList();
    }

    @Cacheable(value = "movies", key = "#movieId + '_' + #language")
    public MovieDb getMovieDetails(int movieId, String language) {
        try {
            return tmdbMovies.getDetails(movieId, language);
        } catch (TmdbException e) {
            return null;
        }
    }

    @SneakyThrows
    @Cacheable(value = "credits", key = "#movieId + '_' + #language")
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

    @Override
    public Optional<Crew> getDirector(MovieDb source) {
        return source.getCredits()
                     .getCrew()
                     .stream()
                     .filter(d -> d.getJob().equals("Director"))
                     .findFirst();
    }

    @Override
    public List<Cast> getCasting(MovieDb source) {
        return source.getCredits()
                     .getCast()
                     .stream()
                     .toList();
    }
}

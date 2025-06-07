package com.xpvault.backend.controller;

import com.xpvault.backend.dto.BasicMovieDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.facade.MovieFacade;
import info.movito.themoviedbapi.model.movies.MovieDb;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.xpvault.backend.literals.constants.AppConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MOVIES_PATH)
@Getter(AccessLevel.PROTECTED)
public class MovieController {

    private final MovieFacade movieFacade;

    @GetMapping(POPULAR_PATH)
    public ResponseEntity<Object> getPopularMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "US") String region,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicMovieDTO> movies = movieFacade.getPopularMovies(language, page, region);

        if (movies == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(POPULAR_MOVIE_NOT_FOUND);
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping(TOP_RATED_PATH)
    public ResponseEntity<Object> getTopRatedMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "US") String region,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicMovieDTO> movies = movieFacade.getTopRatedMovies(language, page, region);

        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TOP_RATED_MOVIE_NOT_FOUND);
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping(UPCOMING_PATH)
    public ResponseEntity<Object> getUpcomingMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "US") String region,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicMovieDTO> movies = movieFacade.getUpcomingMovies(language, page, region);

        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UPCOMING_MOVIE_NOT_FOUND);
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Object> getMovieByTitle(
            @PathVariable String title,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "US") String region,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicMovieDTO> movies = movieFacade.getMovieByTitle(title, language, page, region);

        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_TITLE_NOT_FOUND + title);
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> getMovieByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "1") int page
    ) {
        List<BasicMovieDTO> movies = movieFacade.getMovieByGenre(genre, "en", page);

        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_GENRE_NOT_FOUND + genre);
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getMovieById(
            @PathVariable Integer id,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        MovieDTO movie = movieFacade.getMovieDetailsById(id, language);

        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND + id);
        }

        return ResponseEntity.ok(movie);
    }

    @GetMapping("/id/full/{id}")
    public ResponseEntity<Object> getFullMovieById(
            @PathVariable Integer id,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        MovieDb movie = movieFacade.getMovieFullDetailsById(id, language);

        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND + id);
        }

        return ResponseEntity.ok(movie);
    }
}

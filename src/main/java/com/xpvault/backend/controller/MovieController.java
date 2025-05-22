package com.xpvault.backend.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
@Getter(AccessLevel.PROTECTED)
public class MovieController {

    private final MovieFacade movieFacade;

    @GetMapping("/popular")
    public ResponseEntity<Object> getPopularMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "US") String region,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<MovieDTO> movies = movieFacade.getPopularMovies(language, page, region);

        if (movies == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No popular movies found.");
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<Object> getTopRatedMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "US") String region,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<MovieDTO> movies = movieFacade.getTopRatedMovies(language, page, region);

        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No top-rated movies found.");
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Object> getUpcomingMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "US") String region,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<MovieDTO> movies = movieFacade.getUpcomingMovies(language, page, region);

        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No upcoming movies found.");
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Object> getMovieByTitle(
            @PathVariable String title,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "US") String region,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<MovieDTO> movies = movieFacade.getMovieByTitle(title, language, page, region);

        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No movies found with title: " + title);
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> getMovieByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "1") int page,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<MovieDTO> movies = movieFacade.getMovieByGenre(genre, language, page);

        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No movies found with genre: " + genre);
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getMovieById(
            @PathVariable Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        MovieDTO movie = movieFacade.getMovieDetailsById(id, language);

        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No movies found with id: " + id);
        }

        return ResponseEntity.ok(movie);
    }

    @GetMapping("/id/full/{id}")
    public ResponseEntity<Object> getFullMovieById(
            @PathVariable Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        MovieDb movie = movieFacade.getMovieFullDetailsById(id, language);

        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No movies found with id: " + id);
        }

        return ResponseEntity.ok(movie);
    }
}

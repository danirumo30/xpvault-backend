package com.xpvault.backend.controller;

import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.facade.MovieFacade;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/search")
    public ResponseEntity<Object> getMovieByTitle(
            @RequestParam String title,
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
}

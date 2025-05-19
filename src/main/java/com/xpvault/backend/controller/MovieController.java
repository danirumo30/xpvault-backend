package com.xpvault.backend.controller;

import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.facade.MovieFacade;
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
                    .body("No featured games.");
        }

        return ResponseEntity.ok(movies);
    }
}

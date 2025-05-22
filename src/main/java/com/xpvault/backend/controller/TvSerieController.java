package com.xpvault.backend.controller;

import com.xpvault.backend.dto.BasicTvSerieDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.facade.TvSerieFacade;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
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
@RequestMapping(TV_SERIES_PATH)
@Getter(AccessLevel.PROTECTED)
public class TvSerieController {

    private final TvSerieFacade tvSerieFacade;

    @GetMapping(POPULAR_PATH)
    public ResponseEntity<Object> getPopularTvSeries(
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicTvSerieDTO> tvSeries = tvSerieFacade.getPopularTvSeries(language, page);

        if (tvSeries == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(POPULAR_TV_SERIE_NOT_FOUND);
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping(POPULAR_PATH + "/full")
    public ResponseEntity<Object> getPopularTvSeriesFull(
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<TvSeriesDb> tvSeries = tvSerieFacade.getPopularTvSeriesFull(language, page);

        if (tvSeries == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(POPULAR_TV_SERIE_NOT_FOUND);
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping(TOP_RATED_PATH)
    public ResponseEntity<Object> getTopRatedTvSeries(
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicTvSerieDTO> tvSeries = tvSerieFacade.getTopRatedTvSeries(language, page);

        if (tvSeries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TOP_RATED_TV_SERIE_NOT_FOUND);
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Object> getTvSeriesByTitle(
            @PathVariable String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicTvSerieDTO> tvSeries = tvSerieFacade.getTvSeriesByTitle(title, language, page);

        if (tvSeries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TV_SERIE_TITLE_NOT_FOUND + title);
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> getTvSeriesByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicTvSerieDTO> tvSeries = tvSerieFacade.getTvSeriesByGenre(genre, language, page);

        if (tvSeries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TV_SERIE_GENRE_NOT_FOUND + genre);
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getTvSeriesById(
            @PathVariable Integer id,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        TvSerieDTO tvSerie = tvSerieFacade.getTvSerieDetailsById(id, language);

        if (tvSerie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TV_SERIE_NOT_FOUND + id);
        }

        return ResponseEntity.ok(tvSerie);
    }
}

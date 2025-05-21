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

@RestController
@RequiredArgsConstructor
@RequestMapping("/tv-series")
@Getter(AccessLevel.PROTECTED)
public class TvSerieController {

    private final TvSerieFacade tvSerieFacade;

    @GetMapping("/popular")
    public ResponseEntity<Object> getPopularTvSeries(
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<BasicTvSerieDTO> tvSeries = tvSerieFacade.getPopularTvSeries(language, page);

        if (tvSeries == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No popular tv series found.");
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping("/popular/full")
    public ResponseEntity<Object> getPopularTvSeriesFull(
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<TvSeriesDb> tvSeries = tvSerieFacade.getPopularTvSeriesFull(language, page);

        if (tvSeries == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No popular tv series found.");
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<Object> getTopRatedTvSeries(
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<BasicTvSerieDTO> tvSeries = tvSerieFacade.getTopRatedTvSeries(language, page);

        if (tvSeries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No top-rated tv series found.");
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Object> getTvSeriesByTitle(
            @PathVariable String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<BasicTvSerieDTO> tvSeries = tvSerieFacade.getTvSeriesByTitle(title, language, page);

        if (tvSeries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tv series found with title: " + title);
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> getTvSeriesByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<BasicTvSerieDTO> tvSeries = tvSerieFacade.getTvSeriesByGenre(genre, language, page);

        if (tvSeries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tv series found with genre: " + genre);
        }

        return ResponseEntity.ok(tvSeries);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getTvSeriesById(
            @PathVariable Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        TvSerieDTO tvSerie = tvSerieFacade.getTvSerieDetailsById(id, language);

        if (tvSerie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tv series found with id: " + id);
        }

        return ResponseEntity.ok(tvSerie);
    }
}

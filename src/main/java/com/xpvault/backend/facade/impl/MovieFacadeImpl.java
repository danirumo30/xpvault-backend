package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.MovieDBToMovieDTOConverter;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.facade.MovieFacade;
import com.xpvault.backend.service.MovieService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class MovieFacadeImpl implements MovieFacade {

    private final MovieService movieService;
    private final MovieDBToMovieDTOConverter movieDBToMovieDTOConverter;

    @Override
    public List<MovieDTO> getPopularMovies(String language, int page, String region) {
        return movieService.getPopularMovies(language, page, region)
                           .stream()
                           .map(movieDBToMovieDTOConverter::convert)
                           .toList();
    }

    @Override
    public List<MovieDTO> getTopRatedMovies(String language, int page, String region) {
        return movieService.getTopRatedMovies(language, page, region)
                           .stream()
                           .map(movieDBToMovieDTOConverter::convert)
                           .toList();
    }

    @Override
    public List<MovieDTO> getUpcomingMovies(String language, int page, String region) {
        return movieService.getUpcomingMovies(language, page, region)
                           .stream()
                           .map(movieDBToMovieDTOConverter::convert)
                           .toList();
    }

    @Override
    public List<MovieDTO> getMovieByTitle(String title, String language, int page, String region) {
        return movieService.getMovieByTitle(title, language, page, region)
                           .stream()
                           .map(movieDBToMovieDTOConverter::convert)
                           .toList();
    }
}

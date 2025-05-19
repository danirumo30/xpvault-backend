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
@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
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
}

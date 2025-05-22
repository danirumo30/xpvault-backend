package com.xpvault.backend.converter;

import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.model.MovieModel;
import com.xpvault.backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieModelToMovieDTOConverter implements Converter<MovieModel, MovieDTO> {

    private final MovieService movieService;
    private final MovieDBToMovieDTOConverter movieDBToMovieDTOConverter;

    @Override
    public MovieDTO convert(MovieModel source) {
        return movieDBToMovieDTOConverter.convert(movieService.getMovieDetails(source.getTmdbId(), "en"));
    }
}

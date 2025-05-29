package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicMovieDTO;
import info.movito.themoviedbapi.model.movies.MovieDb;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieDBToBasicMovieDTOConverter implements Converter<MovieDb, BasicMovieDTO> {

    @Override
    public BasicMovieDTO convert(MovieDb source) {
        return new BasicMovieDTO(
                source.getId(),
                "https://image.tmdb.org/t/p/w500" + source.getPosterPath(),
                source.getTitle()
        );
    }
}

package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicCastDTO;
import com.xpvault.backend.dto.BasicDirectorDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.service.MovieService;
import info.movito.themoviedbapi.model.movies.MovieDb;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieDBToMovieDTOConverter implements Converter<MovieDb, MovieDTO> {

    private final CrewToBasicDirectorDTOConverter crewToBasicDirectorDTOConverter;
    private final MovieCastToBasicCastDTOConverter movieCastToBasicCastDTOConverter;
    private final MovieService movieService;

    @Override
    public MovieDTO convert(MovieDb source) {

        Optional<BasicDirectorDTO> director = Optional.empty();
        List<BasicCastDTO> casting = null;

        if (source.getCredits() != null) {
            director = movieService.getDirector(source)
                                   .map(crewToBasicDirectorDTOConverter::convert);

            casting = movieService.getCasting(source)
                                  .stream()
                                  .map(movieCastToBasicCastDTOConverter::convert)
                                  .toList();
        }

        return new MovieDTO(
                source.getId(),
                "https://image.tmdb.org/t/p/w500" + source.getPosterPath(),
                "https://image.tmdb.org/t/p/w500" + source.getBackdropPath(),
                source.getTitle(),
                source.getOverview(),
                source.getReleaseDate(),
                source.getVoteAverage(),
                source.getRuntime(),
                director.orElse(null),
                casting,
                movieService.getMovieGenres(source)
        );
    }
}

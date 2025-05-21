package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicCastDTO;
import com.xpvault.backend.dto.BasicDirectorDTO;
import com.xpvault.backend.dto.MovieDTO;
import info.movito.themoviedbapi.model.core.Genre;
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

    @Override
    public MovieDTO convert(MovieDb source) {

        Optional<BasicDirectorDTO> director = Optional.empty();
        List<BasicCastDTO> casting = null;

        List<String> genres = source.getGenres()
                .stream()
                .map(Genre::getName)
                .toList();

        if (source.getCredits() != null) {
            director = source.getCredits()
                    .getCrew()
                    .stream()
                    .filter(d -> d.getJob().equals("Director"))
                    .findFirst()
                    .map(crewToBasicDirectorDTOConverter::convert);

            casting = source.getCredits()
                    .getCast()
                    .stream()
                    .map(movieCastToBasicCastDTOConverter::convert)
                    .toList();
        }

        return new MovieDTO(
                source.getId(),
                "https://image.tmdb.org/t/p/w500" + source.getPosterPath(),
                source.getTitle(),
                source.getOverview(),
                source.getReleaseDate(),
                source.getVoteAverage(),
                director.orElse(null),
                casting,
                genres
        );
    }
}

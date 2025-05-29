package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.CrewToBasicDirectorDTOConverter;
import com.xpvault.backend.converter.MovieCastToBasicCastDTOConverter;
import com.xpvault.backend.converter.MovieDBToBasicMovieDTOConverter;
import com.xpvault.backend.converter.MovieDBToMovieDTOConverter;
import com.xpvault.backend.dto.BasicCastDTO;
import com.xpvault.backend.dto.BasicDirectorDTO;
import com.xpvault.backend.dto.BasicMovieDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.facade.MovieFacade;
import com.xpvault.backend.service.MovieService;
import info.movito.themoviedbapi.model.movies.MovieDb;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class MovieFacadeImpl implements MovieFacade {

    private final MovieService movieService;
    private final MovieDBToBasicMovieDTOConverter movieDBToBasicMovieDTOConverter;
    private final MovieDBToMovieDTOConverter movieDBToMovieDTOConverter;
    private final CrewToBasicDirectorDTOConverter crewToBasicDirectorDTOConverter;
    private final MovieCastToBasicCastDTOConverter movieCastToBasicCastDTOConverter;

    @Override
    public List<BasicMovieDTO> getPopularMovies(String language, int page, String region) {
        return movieService.getPopularMovies(language, page, region)
                           .stream()
                           .map(movieDBToBasicMovieDTOConverter::convert)
                           .toList();
    }

    @Override
    public List<BasicMovieDTO> getTopRatedMovies(String language, int page, String region) {
        return movieService.getTopRatedMovies(language, page, region)
                           .stream()
                           .map(movieDBToBasicMovieDTOConverter::convert)
                           .toList();
    }

    @Override
    public List<BasicMovieDTO> getUpcomingMovies(String language, int page, String region) {
        return movieService.getUpcomingMovies(language, page, region)
                           .stream()
                           .map(movieDBToBasicMovieDTOConverter::convert)
                           .toList();
    }

    @Override
    public List<BasicMovieDTO> getMovieByTitle(String title, String language, int page, String region) {
        return movieService.getMovieByTitle(title, language, page, region)
                           .stream()
                           .map(movieDBToBasicMovieDTOConverter::convert)
                           .toList();
    }

    @Override
    public List<BasicMovieDTO> getMovieByGenre(String genre, String language, int page) {
        return movieService.getMovieByGenre(genre, language, page)
                           .stream()
                           .map(movieDBToBasicMovieDTOConverter::convert)
                           .toList();
    }

    @Override
    public MovieDTO getMovieDetailsById(int id, String language) {
        return Optional.of(movieService.getMovieDetails(id, language))
                       .map(movie -> {
                            movie.setCredits(movieService.getMovieCredits(id, language));
                            return movie;
                       })
                       .map(movieDBToMovieDTOConverter::convert)
                       .orElse(null);
    }

    @Override
    public MovieDb getMovieFullDetailsById(int id, String language) {
        return movieService.getMovieDetails(id, language);
    }

    @Override
    public Optional<BasicDirectorDTO> getBasicDirectorDTO(MovieDb source) {
        return movieService.getDirector(source)
                            .map(crewToBasicDirectorDTOConverter::convert);
    }

    @Override
    public List<BasicCastDTO> getBasicCastDTO(MovieDb source) {
        return movieService.getCasting(source)
                           .stream()
                           .map(movieCastToBasicCastDTOConverter::convert)
                           .toList();
    }
}

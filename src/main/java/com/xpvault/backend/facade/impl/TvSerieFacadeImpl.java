package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.CreatedByToBasicDirectorDTOConverter;
import com.xpvault.backend.converter.TvSeasonDBToTvSeasonDTOConverter;
import com.xpvault.backend.converter.TvSerieCastToBasicCastDTOConverter;
import com.xpvault.backend.converter.TvSerieDBToBasicTvSerieDTOConverter;
import com.xpvault.backend.converter.TvSerieDBToTvSerieDTOConverter;
import com.xpvault.backend.dto.BasicCastDTO;
import com.xpvault.backend.dto.BasicDirectorDTO;
import com.xpvault.backend.dto.BasicTvSerieDTO;
import com.xpvault.backend.dto.TvSeasonDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.facade.TvSerieFacade;
import com.xpvault.backend.service.TvSerieService;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class TvSerieFacadeImpl implements TvSerieFacade {

    private final TvSerieService tvSerieService;
    private final TvSerieDBToTvSerieDTOConverter tvSerieDBToTvSerieDTOConverter;
    private final TvSerieDBToBasicTvSerieDTOConverter tvSerieDBToBasicTvSerieDTOConverter;
    private final CreatedByToBasicDirectorDTOConverter createdByToBasicDirectorDTOConverter;
    private final TvSerieCastToBasicCastDTOConverter tvSerieCastToBasicCastDTOConverter;
    private final TvSeasonDBToTvSeasonDTOConverter tvSeasonDBToTvSeasonDTOConverter;

    @Override
    public List<BasicTvSerieDTO> getPopularTvSeries(String language, int page) {
        return tvSerieService.getPopularTvSeries(language, page)
                             .stream()
                             .map(tvSerieDBToBasicTvSerieDTOConverter::convert)
                             .toList();
    }

    @Override
    public List<TvSeriesDb> getPopularTvSeriesFull(String language, int page) {
        return tvSerieService.getPopularTvSeries(language, page);
    }

    @Override
    public List<BasicTvSerieDTO> getTopRatedTvSeries(String language, int page) {
        return tvSerieService.getTopRatedTvSeries(language, page)
                             .stream()
                             .map(tvSerieDBToBasicTvSerieDTOConverter::convert)
                             .toList();
    }

    @Override
    public List<BasicTvSerieDTO> getTvSeriesByTitle(String title, String language, int page) {
        return tvSerieService.getTvSeriesByTitle(title, language, page)
                             .stream()
                             .map(tvSerieDBToBasicTvSerieDTOConverter::convert)
                             .toList();
    }

    @Override
    public List<BasicTvSerieDTO> getTvSeriesByGenre(String genre, String language, int page) {
        return tvSerieService.getTvSeriesByGenre(genre, language, page)
                             .stream()
                             .map(tvSerieDBToBasicTvSerieDTOConverter::convert)
                             .toList();
    }

    @Override
    public TvSerieDTO getTvSerieDetailsById(int id, String language) {
        return Optional.of(tvSerieService.getTvSerieDetails(id, language))
                       .map(serie -> {
                            serie.setCredits(tvSerieService.getTvSerieCredits(id, language));
                            return serie;
                       })
                       .map(tvSerieDBToTvSerieDTOConverter::convert)
                       .orElse(null);
    }

    @Override
    public List<BasicDirectorDTO> getDirectors(TvSeriesDb source) {
        return tvSerieService.getDirectors(source)
                             .stream()
                             .map(createdByToBasicDirectorDTOConverter::convert)
                             .toList();
    }

    @Override
    public List<BasicCastDTO> getCasting(TvSeriesDb source) {
        return tvSerieService.getCasting(source)
                             .stream()
                             .map(tvSerieCastToBasicCastDTOConverter::convert)
                             .toList();
    }

    @Override
    public TvSeasonDTO getTvSerieSeasons(int tvSerieId, String language, int seasonNumber) {
        return tvSeasonDBToTvSeasonDTOConverter.convert(tvSerieService.getTvSerieSeasons(tvSerieId, language, seasonNumber), tvSerieId, language);
    }
}

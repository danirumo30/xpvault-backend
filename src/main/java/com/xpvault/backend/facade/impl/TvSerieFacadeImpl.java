package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.TvSerieDBToTvSerieDTOConverter;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.facade.TvSerieFacade;
import com.xpvault.backend.service.TvSerieService;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class TvSerieFacadeImpl implements TvSerieFacade {

    private final TvSerieService tvSerieService;
    private final TvSerieDBToTvSerieDTOConverter tvSerieDBToTvSerieDTOConverter;

    @Override
    public List<TvSerieDTO> getPopularTvSeries(String language, int page) {
        return tvSerieService.getPopularTvSeries(language, page)
                             .stream()
                             .map(tvSerieDBToTvSerieDTOConverter::convert)
                             .toList();
    }

    @Override
    public List<TvSeriesDb> getPopularTvSeriesFull(String language, int page) {
        return tvSerieService.getPopularTvSeries(language, page);
    }

    @Override
    public List<TvSerieDTO> getTopRatedTvSeries(String language, int page) {
        return tvSerieService.getTopRatedTvSeries(language, page)
                             .stream()
                             .map(tvSerieDBToTvSerieDTOConverter::convert)
                             .toList();
    }

    @Override
    public List<TvSerieDTO> getTvSeriesByTitle(String title, String language, int page) {
        return tvSerieService.getTvSeriesByTitle(title, language, page)
                             .stream()
                             .map(tvSerieDBToTvSerieDTOConverter::convert)
                             .toList();
    }
}

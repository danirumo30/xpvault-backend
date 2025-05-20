package com.xpvault.backend.facade;

import com.xpvault.backend.dto.TvSerieDTO;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;

import java.util.List;

public interface TvSerieFacade {

    List<TvSerieDTO> getPopularTvSeries(String language, int page);
    List<TvSeriesDb> getPopularTvSeriesFull(String language, int page);
    List<TvSerieDTO> getTopRatedTvSeries(String language, int page);
    List<TvSerieDTO> getTvSeriesByTitle(String title, String language, int page);

}

package com.xpvault.backend.facade;

import com.xpvault.backend.dto.BasicCastDTO;
import com.xpvault.backend.dto.BasicDirectorDTO;
import com.xpvault.backend.dto.BasicTvSerieDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;

import java.util.List;

public interface TvSerieFacade {

    List<BasicTvSerieDTO> getPopularTvSeries(String language, int page);
    List<TvSeriesDb> getPopularTvSeriesFull(String language, int page);
    List<BasicTvSerieDTO> getTopRatedTvSeries(String language, int page);
    List<BasicTvSerieDTO> getTvSeriesByTitle(String title, String language, int page);
    List<BasicTvSerieDTO> getTvSeriesByGenre(String genre, String language, int page);
    TvSerieDTO getTvSerieDetailsById(int id, String language);
    List<BasicDirectorDTO> getDirectors(TvSeriesDb source);
    List<BasicCastDTO> getCasting(TvSeriesDb source);

}

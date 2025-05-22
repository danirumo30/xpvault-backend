package com.xpvault.backend.service;

import com.xpvault.backend.model.TvSerieModel;
import info.movito.themoviedbapi.model.tv.core.credits.Credits;
import info.movito.themoviedbapi.model.tv.episode.TvEpisodeDb;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;

import java.util.List;

public interface TvSerieService {

    List<TvSeriesDb> getPopularTvSeries(String language, int page);
    List<TvSeriesDb> getTopRatedTvSeries(String language, int page);
    List<TvSeriesDb> getTvSeriesByTitle(String title, String language, int page);
    List<TvSeriesDb> getTvSeriesByGenre(String genre, String language, int page);
    TvSeasonDb getTvSerieSeasons(int tvSerieId, String language, int seasonNumber);
    TvEpisodeDb getTvSerieEpisodes(int tvSerieId, String language, int seasonNumber, int episodeNumber);
    TvSeriesDb getTvSerieDetails(int tvSerieId, String language);
    Credits getTvSerieCredits(int tvSerieId, String language);
    Integer getTotalTvSerieTime(TvSeriesDb tvSeriesDb);
    List<String> getTvSerieGenres(TvSeriesDb tvSeriesDb);
    TvSerieModel findByTmdbId(Integer tvSerieId);

}

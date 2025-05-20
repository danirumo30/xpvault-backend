package com.xpvault.backend.service.impl;


import com.xpvault.backend.service.TvSerieService;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TmdbTvEpisodes;
import info.movito.themoviedbapi.TmdbTvSeasons;
import info.movito.themoviedbapi.TmdbTvSeries;
import info.movito.themoviedbapi.TmdbTvSeriesLists;
import info.movito.themoviedbapi.model.tv.core.credits.Credits;
import info.movito.themoviedbapi.model.tv.episode.TvEpisodeDb;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class TvSerieServiceImpl implements TvSerieService {

    private final TmdbTvEpisodes tmdbTvEpisodes;
    private final TmdbTvSeasons tmdbTvSeasons;
    private final TmdbTvSeries tmdbTvSeries;
    private final TmdbTvSeriesLists tmdbTvSeriesLists;
    private final TmdbSearch tmdbSearch;

    @SneakyThrows
    @Override
    public List<TvSeriesDb> getPopularTvSeries(String language, int page) {
        return tmdbTvSeriesLists.getPopular(language, page)
                                .getResults()
                                .stream()
                                .map(tvSerie-> {
                                    TvSeriesDb tvSeriesDb = getTvSerieDetails(tvSerie.getId(), language);
                                    tvSeriesDb.setCredits(getTvSerieCredits(tvSerie.getId(), language));
                                    return tvSeriesDb;
                                })
                                .toList();
    }

    @SneakyThrows
    @Override
    public List<TvSeriesDb> getTopRatedTvSeries(String language, int page) {
        return tmdbTvSeriesLists.getTopRated(language, page)
                                .getResults()
                                .stream()
                                .map(tvSerie-> {
                                    TvSeriesDb tvSeriesDb = getTvSerieDetails(tvSerie.getId(), language);
                                    tvSeriesDb.setCredits(getTvSerieCredits(tvSerie.getId(), language));
                                    return tvSeriesDb;
                                })
                                .toList();
    }

    @SneakyThrows
    @Override
    public List<TvSeriesDb> getTvSeriesByTitle(String title, String language, int page) {
        return tmdbSearch.searchTv(title, null, false, language, page, null)
                         .getResults()
                         .stream()
                         .map(tvSerie-> {
                                TvSeriesDb tvSeriesDb = getTvSerieDetails(tvSerie.getId(), language);
                                tvSeriesDb.setCredits(getTvSerieCredits(tvSerie.getId(), language));
                                return tvSeriesDb;
                         })
                         .toList();
    }

    @SneakyThrows
    private TvSeriesDb getTvSerieDetails(int tvSerieId, String language) {
        return tmdbTvSeries.getDetails(tvSerieId, language);
    }

    @SneakyThrows
    private Credits getTvSerieCredits(int tvSerieId, String language) {
        return tmdbTvSeries.getCredits(tvSerieId, language);
    }

    @SneakyThrows
    public TvSeasonDb getTvSerieSeasons(int tvSerieId, String language, int seasonNumber) {
        return tmdbTvSeasons.getDetails(tvSerieId, seasonNumber, language);
    }

    @SneakyThrows
    public TvEpisodeDb getTvSerieEpisodes(int tvSerieId, String language, int seasonNumber, int episodeNumber) {
        return tmdbTvEpisodes.getDetails(tvSerieId, seasonNumber, episodeNumber, language);
    }
}

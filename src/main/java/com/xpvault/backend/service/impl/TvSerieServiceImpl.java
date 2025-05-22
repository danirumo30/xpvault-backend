package com.xpvault.backend.service.impl;

import com.xpvault.backend.dao.TvSerieDAO;
import com.xpvault.backend.model.TvSerieModel;
import com.xpvault.backend.service.TvSerieService;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbGenre;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TmdbTvEpisodes;
import info.movito.themoviedbapi.TmdbTvSeasons;
import info.movito.themoviedbapi.TmdbTvSeries;
import info.movito.themoviedbapi.TmdbTvSeriesLists;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.core.IdElement;
import info.movito.themoviedbapi.model.tv.core.credits.Cast;
import info.movito.themoviedbapi.model.tv.core.credits.Credits;
import info.movito.themoviedbapi.model.tv.episode.TvEpisodeDb;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.series.CreatedBy;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.builders.discover.DiscoverTvParamBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class TvSerieServiceImpl implements TvSerieService {

    private final TvSerieDAO tvSerieDAO;
    private final TmdbTvEpisodes tmdbTvEpisodes;
    private final TmdbTvSeasons tmdbTvSeasons;
    private final TmdbTvSeries tmdbTvSeries;
    private final TmdbTvSeriesLists tmdbTvSeriesLists;
    private final TmdbSearch tmdbSearch;
    private final TmdbDiscover tmdbDiscover;
    private final TmdbGenre tmdbGenre;
    private final TvSerieService tvSerieService;

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
    @Override
    public List<TvSeriesDb> getTvSeriesByGenre(String genre, String language, int page) {

        Optional<Integer> genreId = tmdbGenre.getTvList(language)
                                   .stream()
                                   .filter(g -> g.getName().equalsIgnoreCase(genre))
                                   .map(IdElement::getId)
                                   .findFirst();

        DiscoverTvParamBuilder builder = new DiscoverTvParamBuilder().withOriginalLanguage(language)
                                                                     .page(page)
                                                                     .withGenres(List.of(genreId.orElse(0)), false);

        return tmdbDiscover.getTv(builder)
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
    public TvSeriesDb getTvSerieDetails(int tvSerieId, String language) {
        return tmdbTvSeries.getDetails(tvSerieId, language);
    }

    @SneakyThrows
    public Credits getTvSerieCredits(int tvSerieId, String language) {
        return tmdbTvSeries.getCredits(tvSerieId, language);
    }

    @Override
    public Integer getTotalTvSerieTime(TvSeriesDb tvSeriesDb) {
        return tvSeriesDb.getSeasons().stream()
                                      .mapToInt(season -> {
                                            TvSeasonDb tvSeasonDb = getTvSerieSeasons(
                                                    tvSeriesDb.getId(),
                                                    tvSeriesDb.getOriginalLanguage(),
                                                    season.getSeasonNumber()
                                            );
                                            return tvSeasonDb.getEpisodes()
                                                    .stream()
                                                    .mapToInt(episode -> episode.getRuntime() != null ? episode.getRuntime() : 0)
                                                    .sum();
                                      }).sum();
    }

    @Override
    public List<String> getTvSerieGenres(TvSeriesDb tvSeriesDb) {
        return tvSeriesDb.getGenres()
                         .stream()
                         .map(Genre::getName)
                         .toList();
    }

    @Override
    public TvSerieModel findByTmdbId(Integer tvSerieId) {
        return tvSerieDAO.findByTmdbId(tvSerieId);
    }

    @SneakyThrows
    public TvSeasonDb getTvSerieSeasons(int tvSerieId, String language, int seasonNumber) {
        return tmdbTvSeasons.getDetails(tvSerieId, seasonNumber, language);
    }

    @SneakyThrows
    public TvEpisodeDb getTvSerieEpisodes(int tvSerieId, String language, int seasonNumber, int episodeNumber) {
        return tmdbTvEpisodes.getDetails(tvSerieId, seasonNumber, episodeNumber, language);
    }

    @Override
    public List<CreatedBy> getDirectors(TvSeriesDb source) {
        return source.getCreatedBy()
                     .stream()
                     .toList();
    }

    @Override
    public List<Cast> getCasting(TvSeriesDb source) {
        return source.getCredits()
                     .getCast()
                     .stream()
                     .toList();
    }

    @Override
    public int getSeasonTime(TvSeasonDb source) {
        return source.getEpisodes()
                     .stream()
                     .filter(Objects::nonNull)
                     .mapToInt(episode -> {
                        if (episode.getRuntime() != null) {
                            return episode.getRuntime();
                        } else {
                            return 0;
                        }
                    })
                    .sum();
    }
}

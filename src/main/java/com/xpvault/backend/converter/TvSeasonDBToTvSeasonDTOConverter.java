package com.xpvault.backend.converter;

import com.xpvault.backend.dto.TvSeasonDTO;
import com.xpvault.backend.service.TvSerieService;
import info.movito.themoviedbapi.model.tv.episode.TvEpisodeDb;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TvSeasonDBToTvSeasonDTOConverter {

    private final TvSerieService tvSerieService;
    private final TvEpisodeDBToTvEpisodeDTOConverter tvEpisodeDBToTvEpisodeDTOConverter;

    public TvSeasonDTO convert(TvSeasonDb source, Integer tvShowId, String language) {
        return new TvSeasonDTO(
                source.getId(),
                tvShowId,
                source.getName(),
                source.getOverview(),
                source.getSeasonNumber(),
                source.getEpisodes().size(),
                source.getEpisodes().stream()
                                    .map(episode -> {
                                        TvEpisodeDb tvEpisodeDb = tvSerieService.getTvSerieEpisodes(
                                                tvShowId,
                                                language,
                                                source.getSeasonNumber(),
                                                episode.getEpisodeNumber()
                                        );
                                        return tvEpisodeDBToTvEpisodeDTOConverter.convert(tvEpisodeDb);
                                    })
                                    .toList()
        );
    }
}

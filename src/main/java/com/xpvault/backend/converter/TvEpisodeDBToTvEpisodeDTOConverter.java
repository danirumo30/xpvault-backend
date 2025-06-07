package com.xpvault.backend.converter;

import com.xpvault.backend.dto.TvEpisodeDTO;
import info.movito.themoviedbapi.model.tv.episode.TvEpisodeDb;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TvEpisodeDBToTvEpisodeDTOConverter implements Converter<TvEpisodeDb, TvEpisodeDTO> {

    @Override
    public TvEpisodeDTO convert(TvEpisodeDb source) {
        return new TvEpisodeDTO(
                source.getId(),
                source.getName(),
                null,
                source.getRuntime(),
                source.getSeasonNumber(),
                source.getEpisodeNumber()
        );
    }
}

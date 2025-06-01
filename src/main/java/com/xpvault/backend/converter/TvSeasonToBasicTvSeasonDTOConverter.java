package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicTvSeasonDTO;
import info.movito.themoviedbapi.model.tv.core.TvSeason;
import org.springframework.stereotype.Component;

@Component
public class TvSeasonToBasicTvSeasonDTOConverter {

    public BasicTvSeasonDTO convert(TvSeason source, int tvSerieId) {
        return new BasicTvSeasonDTO(
                tvSerieId,
                source.getSeasonNumber(),
                source.getName()
        );
    }
}

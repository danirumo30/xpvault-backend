package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicTvSerieDTO;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TvSerieDBToBasicTvSerieDTOConverter implements Converter<TvSeriesDb, BasicTvSerieDTO> {

    @Override
    public BasicTvSerieDTO convert(TvSeriesDb source) {
        return new BasicTvSerieDTO(
                source.getId(),
                "https://image.tmdb.org/t/p/w500" + source.getPosterPath(),
                source.getName()
        );
    }
}

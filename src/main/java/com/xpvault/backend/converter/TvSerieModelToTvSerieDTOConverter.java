package com.xpvault.backend.converter;

import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.model.TvSerieModel;
import com.xpvault.backend.service.TvSerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TvSerieModelToTvSerieDTOConverter implements Converter<TvSerieModel, TvSerieDTO> {

    private final TvSerieService tvSerieService;
    private final TvSerieDBToTvSerieDTOConverter tvSerieDBToTvSerieDTOConverter;

    @Override
    public TvSerieDTO convert(TvSerieModel source) {
        return tvSerieDBToTvSerieDTOConverter.convert(tvSerieService.getTvSerieDetails(source.getTmdbId(), "en"));
    }
}

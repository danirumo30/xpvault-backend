package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicTvSerieDTO;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TvSerieDBToBasicTvSerieDTOConverter implements Converter<TvSeriesDb, BasicTvSerieDTO> {

    @Override
    public BasicTvSerieDTO convert(TvSeriesDb source) {


        List<String> genres = source.getGenres()
                                    .stream()
                                    .map(Genre::getName)
                                    .toList();


        return new BasicTvSerieDTO(
                source.getId(),
                "https://image.tmdb.org/t/p/w500" + source.getPosterPath(),
                source.getName(),
                source.getOverview(),
                source.getNumberOfSeasons(),
                source.getNumberOfEpisodes(),
                genres
        );
    }
}

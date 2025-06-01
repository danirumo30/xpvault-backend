package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicCastDTO;
import com.xpvault.backend.dto.BasicDirectorDTO;
import com.xpvault.backend.dto.BasicTvSeasonDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.service.TvSerieService;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TvSerieDBToTvSerieDTOConverter implements Converter<TvSeriesDb, TvSerieDTO> {

    private final TvSerieService tvSerieService;
    private final CreatedByToBasicDirectorDTOConverter createdByToBasicDirectorDTOConverter;
    private final TvSerieCastToBasicCastDTOConverter tvSerieCastToBasicCastDTOConverter;
    private final TvSeasonToBasicTvSeasonDTOConverter tvSeasonToBasicTvSeasonDTOConverter;

    @Override
    public TvSerieDTO convert(TvSeriesDb source) {

        List<BasicDirectorDTO> directors = null;
        List<BasicCastDTO> casting = null;


        if (source.getCredits() != null) {
            directors = tvSerieService.getDirectors(source)
                                      .stream()
                                      .map(createdByToBasicDirectorDTOConverter::convert)
                                      .toList();

            casting = tvSerieService.getCasting(source)
                                    .stream()
                                    .map(tvSerieCastToBasicCastDTOConverter::convert)
                                    .toList();
        }

        List<BasicTvSeasonDTO> seasons = source.getSeasons()
                                               .stream()
                                               .map(season -> tvSeasonToBasicTvSeasonDTOConverter.convert(season, source.getId()))
                                               .toList();

        return new TvSerieDTO(
                source.getId(),
                "https://image.tmdb.org/t/p/w500" + source.getPosterPath(),
                "https://image.tmdb.org/t/p/w500" + source.getBackdropPath(),
                source.getName(),
                source.getOverview(),
                source.getFirstAirDate(),
                source.getVoteAverage(),
                source.getNumberOfSeasons(),
                source.getNumberOfEpisodes(),
                tvSerieService.getTotalTvSerieTime(source),
                seasons,
                directors,
                casting,
                tvSerieService.getTvSerieGenres(source)
        );
    }
}

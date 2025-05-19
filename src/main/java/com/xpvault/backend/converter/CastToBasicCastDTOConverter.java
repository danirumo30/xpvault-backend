package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicCastDTO;
import info.movito.themoviedbapi.model.movies.Cast;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CastToBasicCastDTOConverter implements Converter<Cast, BasicCastDTO> {

    @Override
    public BasicCastDTO convert(Cast source) {
        return new BasicCastDTO(
                source.getCreditId(),
                source.getOriginalName(),
                source.getCharacter(),
                "https://image.tmdb.org/t/p/w500" + source.getProfilePath()
        );
    }
}

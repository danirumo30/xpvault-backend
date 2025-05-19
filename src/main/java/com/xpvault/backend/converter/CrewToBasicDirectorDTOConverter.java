package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicDirectorDTO;
import info.movito.themoviedbapi.model.movies.Crew;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewToBasicDirectorDTOConverter implements Converter<Crew, BasicDirectorDTO> {

    @Override
    public BasicDirectorDTO convert(Crew source) {
        return new BasicDirectorDTO(
                source.getCreditId(),
                source.getOriginalName(),
                "https://image.tmdb.org/t/p/w500" + source.getProfilePath()
        );
    }
}

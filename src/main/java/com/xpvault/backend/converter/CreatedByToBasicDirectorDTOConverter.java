package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicDirectorDTO;
import info.movito.themoviedbapi.model.tv.series.CreatedBy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreatedByToBasicDirectorDTOConverter implements Converter<CreatedBy, BasicDirectorDTO> {

    @Override
    public BasicDirectorDTO convert(CreatedBy source) {
        return new BasicDirectorDTO(
                source.getCreditId(),
                source.getName(),
                "https://image.tmdb.org/t/p/w500" + source.getProfilePath()
        );
    }
}

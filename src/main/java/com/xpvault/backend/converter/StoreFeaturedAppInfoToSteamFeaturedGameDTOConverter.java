package com.xpvault.backend.converter;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreFeaturedAppInfo;
import com.xpvault.backend.dto.SteamFeaturedGameDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StoreFeaturedAppInfoToSteamFeaturedGameDTOConverter implements Converter<StoreFeaturedAppInfo, SteamFeaturedGameDTO> {

    @Override
    public SteamFeaturedGameDTO convert(StoreFeaturedAppInfo source) {
        return new SteamFeaturedGameDTO(
                source.getHeaderImageUrl(),
                source.getName(),
                source.getId()
        );
    }
}

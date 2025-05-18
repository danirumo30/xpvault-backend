package com.xpvault.backend.converter;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamApp;
import com.xpvault.backend.dto.BasicGameSteamDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SteamAppToBasicGameSteamDTOConverter implements Converter<SteamApp, BasicGameSteamDTO> {

    @Override
    public BasicGameSteamDTO convert(SteamApp source) {
        return new BasicGameSteamDTO(
                source.getName(),
                source.getAppid()
        );
    }
}

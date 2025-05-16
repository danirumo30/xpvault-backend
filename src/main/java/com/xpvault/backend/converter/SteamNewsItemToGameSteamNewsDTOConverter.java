package com.xpvault.backend.converter;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamNewsItem;
import com.xpvault.backend.dto.GameSteamNewsDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SteamNewsItemToGameSteamNewsDTOConverter implements Converter<SteamNewsItem, GameSteamNewsDTO> {

    @Override
    public GameSteamNewsDTO convert(SteamNewsItem steamNewsItem) {

        return new GameSteamNewsDTO(
                steamNewsItem.getTitle(),
                steamNewsItem.getUrl(),
                steamNewsItem.getAuthor(),
                steamNewsItem.getContents()
        );
    }
}

package com.xpvault.backend.converter;

import com.xpvault.backend.dto.BasicGameSteamDTO;
import com.xpvault.backend.dto.GameSteamDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameSteamDTOToBasicGameSteamDTOConverter implements Converter<GameSteamDTO, BasicGameSteamDTO> {

    @Override
    public BasicGameSteamDTO convert(GameSteamDTO source) {
        return new BasicGameSteamDTO(
                source.getTitle(),
                source.getSteamId(),
                source.getScreenshotUrl(),
                source.getDescription(),
                source.getGenres()
        );
    }
}

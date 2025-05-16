package com.xpvault.backend.converter;

import com.xpvault.backend.dto.GameDTO;
import com.xpvault.backend.model.GameModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameModelToGameDTOConverter implements Converter<GameModel, GameDTO> {

    @Override
    public GameDTO convert(GameModel game) {

        return new GameDTO(
                game.getId(),
                game.getTitle(),
                game.getDescription(),
                game.getSteamId()
        );

    }
}

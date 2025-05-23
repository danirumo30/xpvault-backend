package com.xpvault.backend.converter;

import com.xpvault.backend.dto.GameDTO;
import com.xpvault.backend.model.GameModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameDTOToGameModelConverter implements Converter<GameDTO, GameModel> {

    @Override
    public GameModel convert(GameDTO gameDTO) {
        return new GameModel(
                gameDTO.getId(),
                gameDTO.getTitle(),
                gameDTO.getDescription(),
                gameDTO.getSteamId()
        );

    }
}

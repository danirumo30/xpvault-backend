package com.xpvault.backend.converter;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerOwnedGame;
import com.xpvault.backend.dto.BasicGameSteamDTO;
import com.xpvault.backend.dto.GameSteamDTO;
import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.facade.GameFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SteamPlayerOwnedGameToOwnedSteamGameDTOConverter implements Converter<SteamPlayerOwnedGame, OwnedSteamGameDTO> {

    private final GameFacade gameFacade;
    private final GameSteamDTOToBasicGameSteamDTOConverter steamDTOToBasicGameSteamDTOConverter;


    @Override
    public OwnedSteamGameDTO convert(SteamPlayerOwnedGame source) {

        GameSteamDTO gameSteamDTO = gameFacade.getSteamDetailsBySteamId(source.getAppId(), "en");

        BasicGameSteamDTO basicGameSteamDTO = null;
        if (gameSteamDTO != null) {
            basicGameSteamDTO = steamDTOToBasicGameSteamDTOConverter.convert(gameSteamDTO);
        }

        return new OwnedSteamGameDTO(
                source.getTotalPlaytime(),
                basicGameSteamDTO
        );
    }
}

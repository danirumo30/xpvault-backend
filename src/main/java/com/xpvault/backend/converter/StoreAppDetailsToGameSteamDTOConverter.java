package com.xpvault.backend.converter;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppDetails;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppScreenshots;
import com.xpvault.backend.dto.GameSteamDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class StoreAppDetailsToGameSteamDTOConverter implements Converter<StoreAppDetails, GameSteamDTO> {

    private final Random random = new Random();

    @Override
    public GameSteamDTO convert(StoreAppDetails storeAppDetails) {
        List<StoreAppScreenshots> screenshots = storeAppDetails.getScreenshots();

        if (screenshots == null || screenshots.isEmpty()) {
            return new GameSteamDTO(null);
        }

        int index = random.nextInt(screenshots.size());
        String url = screenshots.get(index).getFullPath();

        return new GameSteamDTO(url);
    }
}

package com.xpvault.backend.converter;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppAchievements;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppDetails;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppGenre;
import com.xpvault.backend.dto.GameSteamDTO;
import com.xpvault.backend.dto.SteamAchievementDTO;
import com.xpvault.backend.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreAppDetailsToGameSteamDTOConverter implements Converter<StoreAppDetails, GameSteamDTO> {

    private final GameService gameService;

    @Override
    public GameSteamDTO convert(StoreAppDetails storeAppDetails) {
        StoreAppAchievements achievements = storeAppDetails.getAchievements();

        List<SteamAchievementDTO> achievementDTOs = null;
        int totalAchievements = 0;
        if (achievements != null && achievements.getAchievementHighlights() != null) {
                totalAchievements = achievements.getTotal();
                achievementDTOs = achievements.getAchievementHighlights()
                                              .stream()
                                              .map(ach ->
                                                      new SteamAchievementDTO(ach.getName(), ach.getPathUrl())
                                              )
                                              .toList();
            }

        return new GameSteamDTO(
                storeAppDetails.getHeaderImageUrl(),
                gameService.getRandomScreenshotUrl(storeAppDetails.getAppId(), "en").orElse(null),
                storeAppDetails.getName(),
                storeAppDetails.getShortDescription(),
                storeAppDetails.getPriceOverview() != null ? storeAppDetails.getPriceOverview().getInitialPrice() : 0,
                storeAppDetails.getAppId(),
                totalAchievements,
                achievementDTOs,
                storeAppDetails.getGenres()
                               .stream()
                               .map(StoreAppGenre::getDescription)
                               .toList()
        );
    }
}

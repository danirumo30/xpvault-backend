package com.xpvault.backend.facade.impl;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamApp;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamNewsItem;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppDetails;
import com.xpvault.backend.converter.GameDTOToGameModelConverter;
import com.xpvault.backend.converter.GameModelToGameDTOConverter;
import com.xpvault.backend.converter.SteamNewsItemToGameSteamNewsDTOConverter;
import com.xpvault.backend.converter.StoreAppDetailsToGameSteamDTOConverter;
import com.xpvault.backend.converter.StoreFeaturedAppInfoToSteamFeaturedGameDTOConverter;
import com.xpvault.backend.dto.GameDTO;
import com.xpvault.backend.dto.GameSteamDTO;
import com.xpvault.backend.dto.GameSteamNewsDTO;
import com.xpvault.backend.dto.SteamFeaturedGameDTO;
import com.xpvault.backend.facade.GameFacade;
import com.xpvault.backend.model.GameModel;
import com.xpvault.backend.service.GameService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class GameFacadeImpl implements GameFacade {

    private final GameService gameService;
    private final GameModelToGameDTOConverter gameModelToGameDTOConverter;
    private final GameDTOToGameModelConverter gameDTOToGameModelConverter;
    private final StoreAppDetailsToGameSteamDTOConverter storeAppDetailsToGameSteamDataDTOConverter;
    private final SteamNewsItemToGameSteamNewsDTOConverter steamNewsItemToGameSteamNewsDataDTOConverter;
    private final StoreFeaturedAppInfoToSteamFeaturedGameDTOConverter storeFeaturedAppInfoToSteamFeaturedGameDTOConverter;

    @Override
    public List<GameDTO> findAll() {
        return gameService.findAll()
                .stream()
                .map(gameModelToGameDTOConverter::convert)
                .toList();
    }

    @Override
    public GameDTO findById(Long id) {
        return gameService.findById(id)
                .map(gameModelToGameDTOConverter::convert)
                .orElse(null);
    }

    @Override
    public List<GameDTO> findByTitle(String title) {
        return gameService.findByTitle(title)
                .stream()
                .map(gameModelToGameDTOConverter::convert)
                .toList();
    }

    @Override
    public void delete(Long id) {
        gameService.delete(id);
    }

    @Override
    public GameDTO save(GameDTO gameDTO) {
        GameModel game = gameDTOToGameModelConverter.convert(gameDTO);

        GameModel saved = gameService.save(game);

        return gameModelToGameDTOConverter.convert(saved);
    }

    @Override
    public GameSteamDTO getSteamDetailsBySteamId(Integer steamId, String language) {
        StoreAppDetails details = gameService.getSteamDetailsBySteamId(steamId, language);

        if (details == null) {
            return null;
        }

        return storeAppDetailsToGameSteamDataDTOConverter.convert(details);
    }

    @Override
    public StoreAppDetails getFullSteamDetailsBySteamId(Integer steamId, String language) {
        return gameService.getSteamDetailsBySteamId(steamId, language);
    }

    @Override
    public List<GameSteamNewsDTO> getSteamNewsBySteamId(Integer steamId) {
        List<SteamNewsItem> steamNewsItems = gameService.getSteamNewsBySteamId(steamId);

        if (steamNewsItems == null) {
            return new ArrayList<>();
        }

        return steamNewsItems.stream()
                             .map(steamNewsItemToGameSteamNewsDataDTOConverter::convert)
                             .toList();
    }

    @Override
    public List<SteamFeaturedGameDTO> getFeaturedGames() {
        return gameService.getFeaturedGames()
                          .getWindowsFeaturedGames()
                          .stream()
                          .map(storeFeaturedAppInfoToSteamFeaturedGameDTOConverter::convert)
                          .toList();
    }

    @Override
    public List<SteamApp> getSteamApps() {
        return gameService.getSteamApps();
    }
}

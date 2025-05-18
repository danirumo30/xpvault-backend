package com.xpvault.backend.service;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamApp;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamNewsItem;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppDetails;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreFeaturedApps;
import com.xpvault.backend.model.GameModel;

import java.util.List;
import java.util.Optional;

public interface GameService {

    List<GameModel> findAll();
    Optional<GameModel> findById(Long id);
    List<GameModel> findByTitle(String title);
    void delete(Long id);
    GameModel save(GameModel game);
    StoreAppDetails getSteamDetailsBySteamId(Integer steamId, String language);
    List<SteamNewsItem> getSteamNewsBySteamId(Integer steamId);
    StoreFeaturedApps getFeaturedGames();
    List<SteamApp> getSteamApps();

}

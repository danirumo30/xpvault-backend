package com.xpvault.backend.facade;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppDetails;
import com.xpvault.backend.dto.BasicGameSteamDTO;
import com.xpvault.backend.dto.GameDTO;
import com.xpvault.backend.dto.GameSteamDTO;
import com.xpvault.backend.dto.GameSteamNewsDTO;
import com.xpvault.backend.dto.SteamFeaturedGameDTO;

import java.util.List;

public interface GameFacade {

    List<GameDTO> findAll();
    GameDTO findById(Long id);
    List<GameDTO> findByTitle(String title);
    void delete(Long id);
    GameDTO save(GameDTO gameDTO);
    GameSteamDTO getSteamDetailsBySteamId(Integer steamId, String language);
    StoreAppDetails getFullSteamDetailsBySteamId(Integer steamId, String language);
    List<GameSteamNewsDTO> getSteamNewsBySteamId(Integer steamId);
    List<SteamFeaturedGameDTO> getFeaturedGames();
    List<BasicGameSteamDTO> getSteamApps();
    List<BasicGameSteamDTO> getSteamAppsWithHeaderImage(int page, int size, String language);
    List<BasicGameSteamDTO> getSteamAppsWithHeaderImageByTitle(String title, int page, int size, String language);

}

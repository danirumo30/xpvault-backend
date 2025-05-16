package com.xpvault.backend.facade;

import com.xpvault.backend.dto.GameDTO;
import com.xpvault.backend.dto.GameSteamDTO;
import com.xpvault.backend.dto.GameSteamNewsDTO;

import java.util.List;

public interface GameFacade {

    List<GameDTO> findAll();
    GameDTO findById(Long id);
    List<GameDTO> findByTitle(String title);
    void delete(Long id);
    GameDTO save(GameDTO gameDTO);
    GameSteamDTO getSteamDetailsBySteamId(Integer steamId, String language);
    List<GameSteamNewsDTO> getSteamNewsBySteamId(Integer steamId);

}

package com.xpvault.backend.controller;

import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.facade.SteamUserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/steam-user")
@RequiredArgsConstructor
public class SteamUserController {

    private final SteamUserFacade steamUserFacade;

    @GetMapping("/owned/{steamId}")
    public ResponseEntity<Object> steamOwnedGames(@PathVariable Long steamId) {
        List<OwnedSteamGameDTO> ownedGames = steamUserFacade.getOwnedGames(steamId);

        if (ownedGames == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No owned games.");
        }
        return ResponseEntity.ok(ownedGames);
    }

    @GetMapping("/top")
    public ResponseEntity<List<SteamUserDTO>> topGamesMostAchievements() {
        List<SteamUserDTO> users = steamUserFacade.getAllUsers();
        return ResponseEntity.ok(users);
    }
}

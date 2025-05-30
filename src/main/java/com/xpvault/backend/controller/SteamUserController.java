package com.xpvault.backend.controller;

import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.dto.AppUserTopDTO;
import com.xpvault.backend.facade.SteamUserFacade;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.xpvault.backend.literals.constants.AppConstants.*;

@RestController
@RequestMapping(STEAM_USER_PATH)
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class SteamUserController {

    private final SteamUserFacade steamUserFacade;

    @GetMapping("/owned/{steamId}")
    public ResponseEntity<Object> steamOwnedGames(@PathVariable Long steamId) {
        List<OwnedSteamGameDTO> ownedGames = steamUserFacade.getOwnedGames(steamId);

        if (ownedGames == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_OWNED_GAMES);
        }
        return ResponseEntity.ok(ownedGames);
    }

    @GetMapping("/owned/ten/{steamId}")
    public ResponseEntity<Object> steamTwentyOwnedGames(@PathVariable Long steamId) {
        List<OwnedSteamGameDTO> ownedGames = steamUserFacade.getTwentyOwnedGames(steamId);

        if (ownedGames == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_OWNED_GAMES);
        }
        return ResponseEntity.ok(ownedGames);
    }

    @GetMapping(TOP_PATH)
    public ResponseEntity<List<AppUserTopDTO>> topGames() {
        List<AppUserTopDTO> users = steamUserFacade.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(RESOLVE_PATH + "/id")
    public ResponseEntity<Object> resolveSteamId(@RequestParam String username) {
        try {
            Long steamId = steamUserFacade.getSteamIdByUsername(username);
            if (steamId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(STEAM_ID_NOT_FOUND_FOR_USERNAME + username);
            }
            return ResponseEntity.ok(steamId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(ERROR_RESOLVING_STEAM_ID + e.getMessage());
        }
    }

    @GetMapping(RESOLVE_PATH + "/username")
    public ResponseEntity<Object> resolveSteamUsername(@RequestParam Long id) {
        try {
            String username = steamUserFacade.getUsernameBySteamId(id);
            if (username == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(USERNAME_NOT_FOUND_FOR_STEAM_ID + id);
            }
            return ResponseEntity.ok(username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ERROR_RESOLVING_STEAM_USERNAME + e.getMessage());
        }
    }

    @GetMapping("/profile/{steamId}")
    public ResponseEntity<Object> getProfile(@PathVariable Long steamId) {
        try {
            SteamUserDTO steamUserDTO = steamUserFacade.getSteamUserById(steamId);
            if (steamUserDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(STEAM_PROFILE_NOT_FOUND + steamId);
            }
            return ResponseEntity.ok(steamUserDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error resolving SteamID: " + e.getMessage());
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Object> newGame(@RequestBody @Valid SteamUserDTO steamUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        SteamUserDTO saved = steamUserFacade.save(steamUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}

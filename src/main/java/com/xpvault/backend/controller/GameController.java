package com.xpvault.backend.controller;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppDetails;
import com.xpvault.backend.dto.BasicGameSteamDTO;
import com.xpvault.backend.dto.GameDTO;
import com.xpvault.backend.dto.GameSteamDTO;
import com.xpvault.backend.dto.GameSteamNewsDTO;
import com.xpvault.backend.dto.SteamFeaturedGameDTO;
import com.xpvault.backend.facade.GameFacade;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.xpvault.backend.literals.constants.AppConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(GAME_PATH)
@Getter(AccessLevel.PROTECTED)
public class GameController {

    private final GameFacade gameFacade;

    @GetMapping("/")
    public List<GameDTO> index() {
        return gameFacade.findAll();
    }

    @GetMapping
    public List<GameDTO> game(@RequestParam(required = false) String title) {
        return gameFacade.findByTitle(title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> game(@PathVariable Long id) {
        GameDTO game = gameFacade.findById(id);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        gameFacade.delete(id);
        return ResponseEntity.ok(SUCCESS_GAME_DELETE);
    }

    @PostMapping("/new")
    public ResponseEntity<Object> newGame(@RequestBody @Valid GameDTO game, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        GameDTO gameDTO = gameFacade.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameDTO);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> edit(@PathVariable Long id, @RequestBody @Valid GameDTO game, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        GameDTO updatedGame = gameFacade.save(game);
        return ResponseEntity.ok(updatedGame);
    }

    @GetMapping(STEAM_PATH + "/details/{steamId}")
    public ResponseEntity<Object> steamDetails(@PathVariable Integer steamId,
                                               @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language) {
        GameSteamDTO steamData = gameFacade.getSteamDetailsBySteamId(steamId, language);

        if (steamData == null || steamData.getScreenshotUrl() == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_STEAM_DETAILS + steamId);
        }
        return ResponseEntity.ok(steamData);
    }

    @GetMapping(STEAM_PATH + "/details/full/{steamId}")
    public ResponseEntity<Object> steamFullDetails(@PathVariable Integer steamId,
                                                   @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language) {
        StoreAppDetails steamData = gameFacade.getFullSteamDetailsBySteamId(steamId, language);

        if (steamData == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_STEAM_DETAILS + steamId);
        }
        return ResponseEntity.ok(steamData);
    }

    @GetMapping(STEAM_PATH + "/featured")
    public ResponseEntity<Object> steamFeaturedGames() {
        List<SteamFeaturedGameDTO> featuredApps = gameFacade.getFeaturedGames();

        if (featuredApps == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_FEATURED_GAME);
        }
        return ResponseEntity.ok(featuredApps);
    }

    @GetMapping(STEAM_PATH + "/news/{steamId}")
    public ResponseEntity<Object> steamNews(@PathVariable Integer steamId) {
        List<GameSteamNewsDTO> news = gameFacade.getSteamNewsBySteamId(steamId);
        if (news == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_STEAM_NEWS + steamId);
        }
        return ResponseEntity.ok(news);
    }

    @GetMapping(STEAM_PATH + "/all")
    public ResponseEntity<Object> steamApss() {
        List<BasicGameSteamDTO> steamApps = gameFacade.getSteamApps();

        if (steamApps == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_STEAM_APPS);
        }
        return ResponseEntity.ok(steamApps);
    }

    @GetMapping(STEAM_PATH + "/{title}")
    public ResponseEntity<Object> steamApss(
            @PathVariable String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicGameSteamDTO> steamApps = gameFacade.getSteamAppsWithHeaderImageByTitle(title, page, size, language);

        if (steamApps == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_GAMES_FOR_TITLE);
        }
        return ResponseEntity.ok(steamApps);
    }

    @GetMapping(STEAM_PATH + "/apps-with-details")
    public ResponseEntity<Object> getSteamAppsWithImages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        List<BasicGameSteamDTO> result = gameFacade.getSteamAppsWithHeaderImage(page, size, language);

        if (result.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_GAMES_FOR_PAGE);
        }

        return ResponseEntity.ok(result);
    }
}

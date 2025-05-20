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

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
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
        return ResponseEntity.ok("Game deleted successfully!");
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

    @GetMapping("/steam/details/{steamId}")
    public ResponseEntity<Object> steamDetails(@PathVariable Integer steamId,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        GameSteamDTO steamData = gameFacade.getSteamDetailsBySteamId(steamId, language);

        if (steamData == null || steamData.getScreenshotUrl() == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No details found for Steam ID: " + steamId);
        }
        return ResponseEntity.ok(steamData);
    }

    @GetMapping("/steam/details/full/{steamId}")
    public ResponseEntity<Object> steamFullDetails(@PathVariable Integer steamId,
                                                   @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        StoreAppDetails steamData = gameFacade.getFullSteamDetailsBySteamId(steamId, language);

        if (steamData == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No details found for Steam ID: " + steamId);
        }
        return ResponseEntity.ok(steamData);
    }

    @GetMapping("/steam/featured")
    public ResponseEntity<Object> steamFeaturedGames() {
        List<SteamFeaturedGameDTO> featuredApps = gameFacade.getFeaturedGames();

        if (featuredApps == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No featured games.");
        }
        return ResponseEntity.ok(featuredApps);
    }

    @GetMapping("/steam/news/{steamId}")
    public ResponseEntity<Object> steamNews(@PathVariable Integer steamId) {
        List<GameSteamNewsDTO> news = gameFacade.getSteamNewsBySteamId(steamId);
        if (news == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No news found for Steam ID: " + steamId);
        }
        return ResponseEntity.ok(news);
    }

    @GetMapping("/steam/all")
    public ResponseEntity<Object> steamApss() {
        List<BasicGameSteamDTO> steamApps = gameFacade.getSteamApps();

        if (steamApps == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No featured games.");
        }
        return ResponseEntity.ok(steamApps);
    }

    @GetMapping("/steam/{title}")
    public ResponseEntity<Object> steamApss(
            @PathVariable String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<BasicGameSteamDTO> steamApps = gameFacade.getSteamAppsWithHeaderImageByTitle(title, page, size, language);

        if (steamApps == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No games for this title.");
        }
        return ResponseEntity.ok(steamApps);
    }

    @GetMapping("/steam/apps-with-details")
    public ResponseEntity<Object> getSteamAppsWithImages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        List<BasicGameSteamDTO> result = gameFacade.getSteamAppsWithHeaderImage(page, size, language);

        if (result.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No Steam apps found for this page.");
        }

        return ResponseEntity.ok(result);
    }
}

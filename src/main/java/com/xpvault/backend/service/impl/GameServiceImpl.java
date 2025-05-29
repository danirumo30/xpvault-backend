package com.xpvault.backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamNews;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamStorefront;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamApp;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamNewsItem;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppDetails;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreFeaturedApps;
import com.xpvault.backend.dao.GameDAO;
import com.xpvault.backend.exception.SteamApiException;
import com.xpvault.backend.model.GameModel;
import com.xpvault.backend.service.GameService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class GameServiceImpl implements GameService {

    private final GameDAO gameDAO;
    private final SteamStorefront steamStorefront;
    private final SteamNews steamNews;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${steam.news.maxLength}")
    private int maxLength;

    @Value("${steam.news.count}")
    private int newsCount;

    @Override
    public List<GameModel> findAll() {
        return gameDAO.findAll();
    }

    @Override
    public Optional<GameModel> findById(Long id) {
        return gameDAO.findById(id);
    }

    @Override
    public List<GameModel> findByTitle(String title) {
        return gameDAO.findByTitleContainsIgnoreCase(title);
    }

    @Override
    public void delete(Long id) {
        gameDAO.deleteById(id);
    }

    @Override
    public GameModel save(GameModel game) {
        return gameDAO.save(game);
    }

    @Override
    public StoreAppDetails getSteamDetailsBySteamId(Integer steamId, String language) {
        try {
            return steamStorefront.getAppDetails(steamId, "US", language)
                                  .thenApply(storeAppDetails -> storeAppDetails)
                                  .join();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SteamNewsItem> getSteamNewsBySteamId(Integer steamId) {
        return steamNews.getNewsForApp(steamId, maxLength, -1, newsCount, "")
                        .thenApply(steamNewsItems -> steamNewsItems)
                        .join();
    }

    @Override
    public StoreFeaturedApps getFeaturedGames() {
        return steamStorefront.getFeaturedApps()
                              .thenApply(featuredApps -> featuredApps)
                              .join();
    }

    @Override
    public List<SteamApp> getSteamApps() {
        String url = "https://api.steampowered.com/IStoreService/GetAppList/v1/?key=F7738655EF1D83B1DFF0C0536CCC31AE&include_games=true&include_dlc=false&include_software=false&include_videos=false&include_hardware=false";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String json = response.getBody();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode appsNode = root.path("response").path("apps");
            List<SteamApp> apps = new ArrayList<>();

            for (JsonNode appNode : appsNode) {
                SteamApp app = new SteamApp();
                app.setAppid(appNode.path("appid").asInt());
                app.setName(appNode.path("name").asText());
                apps.add(app);
            }

            return apps.stream()
                       .filter(app -> app.getName() != null && !app.getName().isBlank())
                       .toList();

        } catch (Exception e) {
            throw new SteamApiException(e.getMessage());
        }
    }

    @Override
    public List<SteamApp> getSteamAppsFilteredByTitle(String title) {
        return getSteamApps().stream()
                             .filter(app -> app.getName().toLowerCase().contains(title.toLowerCase()))
                             .toList();
    }

    @Override
    public List<SteamApp> getSteamAppsFilteredByGenre(String genre) {
        String url = "https://steamspy.com/api.php?request=genre&genre=" + genre;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String json = response.getBody();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            List<SteamApp> apps = new ArrayList<>();

            for (JsonNode appNode : root) {
                SteamApp app = new SteamApp();

                int appid = appNode.path("appid").asInt();
                String name = appNode.path("name").asText();

                app.setAppid(appid);
                app.setName(name);

                if (name != null && !name.isBlank()) {
                    apps.add(app);
                }
            }

            return apps;

        } catch (Exception e) {
            throw new SteamApiException("Error fetching SteamSpy genre data: " + e.getMessage());
        }
    }

    @Override
    public List<SteamApp> getSteamAppsPaged(int page, int size, String language, List<SteamApp> apps) {
        int fromIndex = page * size;
        if (fromIndex >= apps.size()) {
            return List.of();
        }

        int toIndex = Math.min(fromIndex + size, apps.size());
        return apps.subList(fromIndex, toIndex);
    }

    @Override
    public Optional<String> getRandomScreenshotUrl(Integer steamId, String language) {
        StoreAppDetails details = getSteamDetailsBySteamId(steamId, language);
        if (details != null && details.getScreenshots() != null && !details.getScreenshots().isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(details.getScreenshots().size());
            return Optional.ofNullable(details.getScreenshots().get(index).getFullPath());
        }
        return Optional.empty();
    }
}

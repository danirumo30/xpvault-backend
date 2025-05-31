package com.xpvault.backend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
    private final Random random = new Random();

    @Getter(AccessLevel.PUBLIC)
    private final List<SteamApp> steamApps;

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
    public List<SteamApp> getSteamAppsFull() {
        List<SteamApp> apps = new ArrayList<>();
        int page = 0;

        try {
            while (page != 80) {
                String url = "https://steamspy.com/api.php?request=all&page=" + page;
                JsonNode root = getJsonFromUrl(url);

                Iterator<String> fieldNames = root.fieldNames();
                while (fieldNames.hasNext()) {
                    String appidKey = fieldNames.next();
                    JsonNode appNode = root.get(appidKey);

                    SteamApp app = new SteamApp();
                    app.setAppid(appNode.path("appid").asInt());
                    app.setName(appNode.path("name").asText());

                    if (app.getName() != null && !app.getName().isBlank()) {
                        apps.add(app);
                    }
                }

                page++;
            }

            return apps;

        } catch (Exception e) {
            throw new SteamApiException("Error al obtener apps de SteamSpy: " + e.getMessage());
        }
    }

    @Override
    public List<SteamApp> getSteamApps(int page, int size) {
        try {
            List<SteamApp> apps = new ArrayList<>();

            int fromIndex = page * size;
            int toIndex = Math.min(fromIndex + size, steamApps.size());

            for (int i = fromIndex; i < toIndex; i++) {
                apps.add(steamApps.get(i));
            }

            return apps.stream()
                       .filter(app -> app.getName() != null && !app.getName().isBlank())
                       .toList();

        } catch (Exception e) {
            throw new SteamApiException(e.getMessage());
        }
    }

    @Override
    public List<SteamApp> getSteamAppsFilteredByTitle(int page, int size, String title) {
        try {
            List<SteamApp> filteredApps = new ArrayList<>();

            for (SteamApp appNode : steamApps) {
                String name = appNode.getName();
                if (name != null && name.toLowerCase().contains(title.toLowerCase())) {
                    filteredApps.add(appNode);
                }
            }

            int fromIndex = page * size;
            if (fromIndex >= filteredApps.size()) {
                return Collections.emptyList();
            }
            int toIndex = Math.min(fromIndex + size, filteredApps.size());

            return filteredApps.subList(fromIndex, toIndex);

        } catch (Exception e) {
            throw new SteamApiException(e.getMessage());
        }
    }

    @Override
    public List<SteamApp> getSteamAppsFilteredByGenre(int page, int size, String genre) {
        try {
            List<JsonNode> appNodes = getAppsNode(genre);

            int fromIndex = page * size;
            if (fromIndex >= appNodes.size()) {
                return Collections.emptyList();
            }

            int toIndex = Math.min(fromIndex + size, appNodes.size());

            List<SteamApp> apps = new ArrayList<>();

            for (int i = fromIndex; i < toIndex; i++) {
                JsonNode appNode = appNodes.get(i);
                if (appNode == null) {
                    continue;
                }

                int appid = appNode.path("appid").asInt();
                String name = appNode.path("name").asText();

                if (name != null && !name.isBlank()) {
                    SteamApp app = new SteamApp();
                    app.setAppid(appid);
                    app.setName(name);
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
        return List.of();
    }

    @Override
    public Optional<String> getRandomScreenshotUrl(Integer steamId, String language) {
        StoreAppDetails details = getSteamDetailsBySteamId(steamId, language);
        if (details != null && details.getScreenshots() != null && !details.getScreenshots().isEmpty()) {
            int index = random.nextInt(details.getScreenshots().size());
            return Optional.ofNullable(details.getScreenshots().get(index).getFullPath());
        }
        return Optional.empty();
    }

    private List<JsonNode> getAppsNode(String genre) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity("https://steamspy.com/api.php?request=genre&genre=" + genre, String.class);
        String json = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        List<JsonNode> appNodes = new ArrayList<>();
        root.fields().forEachRemaining(entry -> appNodes.add(entry.getValue()));

        return appNodes;
    }

    private JsonNode getJsonFromUrl(String urlStr) throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(urlStr, String.class);
        String json = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(json);
    }
}

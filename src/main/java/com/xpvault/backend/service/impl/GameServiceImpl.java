package com.xpvault.backend.service.impl;

import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamApps;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamNews;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamStorefront;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamApp;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamNewsItem;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreAppDetails;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.StoreFeaturedApps;
import com.xpvault.backend.dao.GameDAO;
import com.xpvault.backend.model.GameModel;
import com.xpvault.backend.service.GameService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    private final SteamApps steamApps;

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
        return steamStorefront.getAppDetails(steamId, "US", language)
                              .thenApply(storeAppDetails -> storeAppDetails)
                              .join();
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
        return steamApps.getAppList()
                        .thenApply(apps -> apps)
                        .join();
    }

    @Override
    public List<SteamApp> getSteamAppsFilteredByTitle(String title) {
        return getSteamApps().stream()
                             .filter(app -> app.getName().toLowerCase().contains(title.toLowerCase()))
                             .toList();
    }

    @Override
    public List<SteamApp> getSteamAppsPaged(int page, int size, String language, List<SteamApp> apps) {
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, apps.size());

        if (fromIndex >= apps.size()) {
            return Collections.emptyList();
        }

        List<SteamApp> currentPage = apps.subList(fromIndex, toIndex);

        return currentPage.stream()
                          .filter(app -> {
                              StoreAppDetails details = getSteamDetailsBySteamId(app.getAppid(), language);
                              return details != null && "game".equalsIgnoreCase(details.getType());
                          })
                          .toList();
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

package com.xpvault.backend.init;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamApp;
import com.xpvault.backend.service.GameService;
import com.xpvault.backend.service.impl.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameInitializer implements ApplicationRunner {

    private final GameService gameService;

    @Override
    public void run(ApplicationArguments args) {

        List<SteamApp> apps = gameService.getSteamAppsFull();

        if (gameService instanceof GameServiceImpl impl) {
            impl.getSteamApps().addAll(apps);
            System.out.println(apps.size());
        }
    }
}

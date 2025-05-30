package com.xpvault.backend.init;

import com.xpvault.backend.dao.GameDAO;
import com.xpvault.backend.dao.UserDAO;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.model.GameModel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.xpvault.backend.literals.constants.AppConstants.ADMIN_MAIL;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements ApplicationRunner {

    private final UserDAO userDAO;
    private final GameDAO gameDAO;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        userDAO.findByEmail(ADMIN_MAIL)
                .ifPresent(user -> userDAO.deleteById(user.getId()));

        if (!userDAO.existsByEmail(ADMIN_MAIL)) {
            AppUserModel admin = AppUserModel.builder()
                    .username("admin")
                    .password("$2a$10$BSFg8tEFD9762qMMxkTbouBhk0EHGTNmiwGNZ3vVLpwJzRiiBIdTS")
                    .email(ADMIN_MAIL)
                    .role("REGISTERED")
                    .enabled(true)
                    .steamUser(null)
                    .build();

            userDAO.save(admin);
        }

        gameDAO.deleteAll();

        List<GameModel> demoGames = List.of(
                GameModel.builder()
                        .title("Counter-Strike 2")
                        .description("Competitive multiplayer shooter with tactical mechanics.")
                        .steamId(730)
                        .build(),
                GameModel.builder()
                        .title("Dota 2")
                        .description("Free MOBA with unique heroes and intense matches.")
                        .steamId(570)
                        .build(),
                GameModel.builder()
                        .title("PUBG: BATTLEGROUNDS")
                        .description("Battle royale where you fight to be the last one standing.")
                        .steamId(578080)
                        .build(),
                GameModel.builder()
                        .title("Apex Legends")
                        .description("Battle royale with unique characters and special abilities.")
                        .steamId(1172470)
                        .build(),
                GameModel.builder()
                        .title("Rust")
                        .description("Survival game set in a hostile open world.")
                        .steamId(252490)
                        .build(),
                GameModel.builder()
                        .title("Baldur's Gate 3")
                        .description("RPG based on Dungeons & Dragons with deep narrative.")
                        .steamId(1086940)
                        .build(),
                GameModel.builder()
                        .title("Cyberpunk 2077")
                        .description("Open-world RPG set in a dystopian future.")
                        .steamId(1091500)
                        .build(),
                GameModel.builder()
                        .title("The Witcher 3: Wild Hunt")
                        .description("Epic adventure of Geralt of Rivia in an open world.")
                        .steamId(292030)
                        .build(),
                GameModel.builder()
                        .title("Stardew Valley")
                        .description("Farming simulator with RPG and social life elements.")
                        .steamId(413150)
                        .build(),
                GameModel.builder()
                        .title("Terraria")
                        .description("2D adventure game with exploration and building.")
                        .steamId(105600)
                        .build(),
                GameModel.builder()
                        .title("Hades")
                        .description("Fast-paced roguelike set in Greek mythology.")
                        .steamId(1145360)
                        .build(),
                GameModel.builder()
                        .title("Slay the Spire")
                        .description("Card game and roguelike with deep strategy.")
                        .steamId(646570)
                        .build(),
                GameModel.builder()
                        .title("Vampire Survivors")
                        .description("Retro-style action where you survive waves of enemies.")
                        .steamId(1794680)
                        .build(),
                GameModel.builder()
                        .title("Left 4 Dead 2")
                        .description("Cooperative shooter against zombie hordes.")
                        .steamId(550)
                        .build(),
                GameModel.builder()
                        .title("Garry's Mod")
                        .description("Sandbox that lets you create and play with physics and mods.")
                        .steamId(4000)
                        .build(),
                GameModel.builder()
                        .title("Euro Truck Simulator 2")
                        .description("Truck driving simulator across Europe.")
                        .steamId(227300)
                        .build(),
                GameModel.builder()
                        .title("Portal 2")
                        .description("First-person puzzle game with portals.")
                        .steamId(620)
                        .build(),
                GameModel.builder()
                        .title("Team Fortress 2")
                        .description("Team-based multiplayer shooter with varied classes.")
                        .steamId(440)
                        .build(),
                GameModel.builder()
                        .title("ARK: Survival Evolved")
                        .description("Survival game in a world full of dinosaurs.")
                        .steamId(346110)
                        .build(),
                GameModel.builder()
                        .title("Among Us")
                        .description("Multiplayer social deduction game in space.")
                        .steamId(945360)
                        .build()
        );

        gameDAO.saveAll(demoGames);
    }
}

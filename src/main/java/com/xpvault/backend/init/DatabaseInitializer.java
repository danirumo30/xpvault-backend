package com.xpvault.backend.init;

import com.xpvault.backend.dao.GameDAO;
import com.xpvault.backend.dao.UserDAO;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.model.GameModel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements ApplicationRunner {

    private final UserDAO userDAO;
    private final GameDAO gameDAO;

    @Override
    public void run(ApplicationArguments args) {
        if (!userDAO.existsByEmail("xpvault.team@gmail.com")) {
            AppUserModel admin = AppUserModel.builder()
                    .username("admin")
                    .password("$2a$10$BSFg8tEFD9762qMMxkTbouBhk0EHGTNmiwGNZ3vVLpwJzRiiBIdTS")
                    .email("xpvault.team@gmail.com")
                    .role("REGISTERED")
                    .enabled(true)
                    .build();

            userDAO.save(admin);
        }

        gameDAO.deleteAll();

        List<GameModel> demoGames = List.of(
                GameModel.builder()
                         .title("Counter-Strike 2")
                         .description("Shooter multijugador competitivo con mecánicas tácticas.")
                         .steamId(730)
                         .build(),
                GameModel.builder()
                         .title("Dota 2")
                         .description("MOBA gratuito con héroes únicos y partidas intensas.")
                         .steamId(570)
                         .build(),
                GameModel.builder()
                         .title("PUBG: BATTLEGROUNDS")
                         .description("Battle royale en el que luchas por ser el último en pie.")
                         .steamId(578080)
                         .build(),
                GameModel.builder()
                         .title("Apex Legends")
                         .description("Battle royale con personajes únicos y habilidades especiales.")
                         .steamId(1172470)
                         .build(),
                GameModel.builder()
                         .title("Rust")
                         .description("Juego de supervivencia en un mundo abierto hostil.")
                         .steamId(252490)
                         .build(),
                GameModel.builder()
                         .title("Baldurs Gate 3")
                         .description("RPG basado en Dungeons & Dragons con narrativa profunda.")
                         .steamId(1086940)
                         .build(),
                GameModel.builder()
                         .title("Cyberpunk 2077")
                         .description("RPG de mundo abierto ambientado en un futuro distópico.")
                         .steamId(1091500)
                         .build(),
                GameModel.builder()
                         .title("The Witcher 3: Wild Hunt")
                         .description("Aventura épica de Geralt de Rivia en un mundo abierto.")
                         .steamId(292030)
                         .build(),
                GameModel.builder()
                         .title("Stardew Valley")
                         .description("Simulador de granja con elementos de RPG y vida social.")
                         .steamId(413150)
                         .build(),
                GameModel.builder()
                         .title("Terraria")
                         .description("Juego de aventuras en 2D con exploración y construcción.")
                         .steamId(105600)
                         .build(),
                GameModel.builder()
                         .title("Hades")
                         .description("Roguelike de acción rápida ambientado en la mitología griega.")
                         .steamId(1145360)
                         .build(),
                GameModel.builder()
                         .title("Slay the Spire")
                         .description("Juego de cartas y roguelike con estrategia profunda.")
                         .steamId(646570)
                         .build(),
                GameModel.builder()
                         .title("Vampire Survivors")
                         .description("Acción retro en la que sobrevives a hordas de enemigos.")
                         .steamId(1794680)
                         .build(),
                GameModel.builder()
                         .title("Left 4 Dead 2")
                         .description("Shooter cooperativo contra hordas de zombis.")
                         .steamId(550)
                         .build(),
                GameModel.builder()
                         .title("Garrys Mod")
                         .description("Sandbox que permite crear y jugar con físicas y mods.")
                         .steamId(4000)
                         .build(),
                GameModel.builder()
                         .title("Euro Truck Simulator 2")
                         .description("Simulador de conducción de camiones por Europa.")
                         .steamId(227300)
                         .build(),
                GameModel.builder()
                         .title("Portal 2")
                         .description("Juego de puzzles en primera persona con portales.")
                         .steamId(620)
                         .build(),
                GameModel.builder()
                         .title("Team Fortress 2")
                         .description("Shooter multijugador por equipos con clases variadas.")
                         .steamId(440)
                         .build(),
                GameModel.builder()
                         .title("ARK: Survival Evolved")
                         .description("Supervivencia en un mundo lleno de dinosaurios.")
                         .steamId(346110)
                         .build(),
                GameModel.builder()
                         .title("Among Us")
                         .description("Juego multijugador de deducción social en el espacio.")
                         .steamId(945360)
                         .build()
        );



        gameDAO.saveAll(demoGames);
    }
}

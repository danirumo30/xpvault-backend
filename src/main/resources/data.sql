-- ADMIN USER
INSERT INTO app_user (id, username, password, email, role, enabled)
VALUES (
           (SELECT COALESCE(MAX(id), 0) + 1 FROM app_user),
           'admin',
           '$2a$10$BSFg8tEFD9762qMMxkTbouBhk0EHGTNmiwGNZ3vVLpwJzRiiBIdTS',
            'xpvault.team@gmail.com',
           'REGISTERED',
           true
       )
ON CONFLICT (email) DO NOTHING;

-- GAME DEMO DATA
DELETE FROM games;
INSERT INTO games (title, description, steam_id) VALUES
                                                     ('Counter-Strike 2', 'Shooter multijugador competitivo con mecánicas tácticas.', 730),
                                                     ('Dota 2', 'MOBA gratuito con héroes únicos y partidas intensas.', 570),
                                                     ('PUBG: BATTLEGROUNDS', 'Battle royale en el que luchas por ser el último en pie.', 578080),
                                                     ('Apex Legends', 'Battle royale con personajes únicos y habilidades especiales.', 1172470),
                                                     ('Rust', 'Juego de supervivencia en un mundo abierto hostil.', 252490),
                                                     ('Baldurs Gate 3', 'RPG basado en Dungeons & Dragons con narrativa profunda.', 1086940),
                                                     ('Cyberpunk 2077', 'RPG de mundo abierto ambientado en un futuro distópico.', 1091500),
                                                     ('The Witcher 3: Wild Hunt', 'Aventura épica de Geralt de Rivia en un mundo abierto.', 292030),
                                                     ('Stardew Valley', 'Simulador de granja con elementos de RPG y vida social.', 413150),
                                                     ('Terraria', 'Juego de aventuras en 2D con exploración y construcción.', 105600),
                                                     ('Hades', 'Roguelike de acción rápida ambientado en la mitología griega.', 1145360),
                                                     ('Slay the Spire', 'Juego de cartas y roguelike con estrategia profunda.', 646570),
                                                     ('Vampire Survivors', 'Acción retro en la que sobrevives a hordas de enemigos.', 1794680),
                                                     ('Left 4 Dead 2', 'Shooter cooperativo contra hordas de zombis.', 550),
                                                     ('Garrys Mod', 'Sandbox que permite crear y jugar con físicas y mods.', 4000),
                                                     ('Euro Truck Simulator 2', 'Simulador de conducción de camiones por Europa.', 227300),
                                                     ('Portal 2', 'Juego de puzzles en primera persona con portales.', 620),
                                                     ('Team Fortress 2', 'Shooter multijugador por equipos con clases variadas.', 440),
                                                     ('ARK: Survival Evolved', 'Supervivencia en un mundo lleno de dinosaurios.', 346110),
                                                     ('Among Us', 'Juego multijugador de deducción social en el espacio.', 945360);

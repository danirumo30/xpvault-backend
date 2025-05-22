package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUserDTO {

    private Long id;
    private byte[] profilePhoto;
    private String username;
    private String email;
    private String password;
    private String verificationCode;
    private LocalDateTime verificationExpiration;
    private SteamUserDTO steamUser;
    private Integer totalTimePlayed;
    private Integer totalTimeMoviesWatched;
    private Integer totalTimeEpisodesWatched;
    private Integer totalGames;
    private Integer totalMovies;
    private Integer totalEpisodes;

}

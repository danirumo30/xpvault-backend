package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUserDetailsDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String verificationCode;
    private LocalDateTime verificationExpiration;
    private SteamUserDTO steamUser;
    private Integer totalTimePlayed;
    private Integer totalTimeMoviesWatched;
    private Integer totalTimeEpisodesWatched;
    private List<MovieDTO> movies;
    private List<TvSerieDTO> tvSeries;
    private List<OwnedSteamGameDTO> games;

}

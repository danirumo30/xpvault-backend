package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUserDetailsDTO {

    private Long id;
    private String username;
    private SteamUserDTO steamUser;
    private Long totalTimePlayed;
    private Integer totalTimeMoviesWatched;
    private Integer totalTimeEpisodesWatched;
    private Integer totalFriends;
    private List<MovieDTO> movies;
    private List<TvSerieDTO> tvSeries;
    private List<OwnedSteamGameDTO> games;

}

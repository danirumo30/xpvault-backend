package com.xpvault.backend.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
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

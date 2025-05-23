package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GameSteamDTO {

    private String screenshotUrl;
    private String title;
    private String description;
    private Integer price;
    private Integer steamId;
    private Integer totalAchievements;
    private List<SteamAchievementDTO> achievements;
    private List<String> genres;

}

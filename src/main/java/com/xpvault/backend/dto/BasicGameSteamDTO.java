package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BasicGameSteamDTO {

    private String title;
    private Integer steamId;
    private String screenshotUrl;

}

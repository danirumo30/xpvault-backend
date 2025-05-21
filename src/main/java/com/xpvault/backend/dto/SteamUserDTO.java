package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SteamUserDTO {

    private Long steamId;
    private String nickname;
    private String avatar;
    private String profileUrl;
    private Long totalTimePlayed;
    private List<OwnedSteamGameDTO> ownedGames;

}

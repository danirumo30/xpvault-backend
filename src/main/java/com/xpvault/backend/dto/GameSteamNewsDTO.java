package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameSteamNewsDTO {

    private String title;
    private String url;
    private String author;
    private String contents;

}

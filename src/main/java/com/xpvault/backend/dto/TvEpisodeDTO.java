package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TvEpisodeDTO {

    private Integer tmdbId;
    private String title;
    private String description;
    private Integer totalTime;
    private Integer seasonNumber;
    private Integer episodeNumber;

}

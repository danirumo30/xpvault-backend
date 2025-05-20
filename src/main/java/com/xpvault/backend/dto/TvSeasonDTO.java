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
public class TvSeasonDTO {

    private Integer tmbdId;
    private Integer tvShowId;
    private String title;
    private String description;
    private Integer seasonNumber;
    private Integer episodesCount;
    private List<TvEpisodeDTO> episodes;

}

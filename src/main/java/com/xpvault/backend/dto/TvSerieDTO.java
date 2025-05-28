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
public class TvSerieDTO {

    private Integer tmdbId;
    private String posterUrl;
    private String headerUrl;
    private String title;
    private String description;
    private String releaseDate;
    private Double rating;
    private Integer totalSeasons;
    private Integer totalEpisodes;
    private Integer totalTime;
    private List<TvSeasonDTO> seasons;
    private List<BasicDirectorDTO> director;
    private List<BasicCastDTO> casting;
    private List<String> genres;

}

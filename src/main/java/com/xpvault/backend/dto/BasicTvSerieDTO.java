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
public class BasicTvSerieDTO {

    private Integer tmbdId;
    private String posterUrl;
    private String title;
    private String description;
    private Integer totalSeasons;
    private Integer totalEpisodes;
    private List<String> genres;

}

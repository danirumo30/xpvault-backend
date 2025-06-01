package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BasicTvSeasonDTO {

    private Integer showId;
    private Integer tmdbId;
    private String name;

}

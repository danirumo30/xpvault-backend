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
public class MovieDTO {

    private Integer tmbdId;
    private String posterUrl;
    private String headerUrl;
    private String title;
    private String description;
    private String releaseDate;
    private Double rating;
    private Integer totalTime;
    private BasicDirectorDTO director;
    private List<BasicCastDTO> casting;
    private List<String> genres;

}

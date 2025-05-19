package com.xpvault.backend.facade;

import com.xpvault.backend.dto.MovieDTO;

import java.util.List;

public interface MovieFacade {

    List<MovieDTO> getPopularMovies(String language, int page, String region);

}

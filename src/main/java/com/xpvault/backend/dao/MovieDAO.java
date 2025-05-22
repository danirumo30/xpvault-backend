package com.xpvault.backend.dao;

import com.xpvault.backend.model.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDAO extends JpaRepository<MovieModel, Long> {

    MovieModel findByTmdbId(Integer tmdbId);

}

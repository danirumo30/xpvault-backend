package com.xpvault.backend.dao;

import com.xpvault.backend.model.TvSerieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvSerieDAO extends JpaRepository<TvSerieModel, Long> {

    TvSerieModel findByTmdbId(Integer tmdbId);

}

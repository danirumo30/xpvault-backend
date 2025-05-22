package com.xpvault.backend.dao;

import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.model.MovieModel;
import com.xpvault.backend.model.TvSerieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<AppUserModel, Long> {

    Optional<AppUserModel> findByEmail(String email);
    AppUserModel findByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u.tvSeries FROM AppUserModel u WHERE u.username = :username")
    List<TvSerieModel> findTvSeriesByUsername(String username);

    @Query("SELECT u.movies FROM AppUserModel u WHERE u.username = :username")
    List<MovieModel> findMoviesByUsername(@Param("username") String username);

}

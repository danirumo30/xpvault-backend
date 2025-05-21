package com.xpvault.backend.dao;

import com.xpvault.backend.model.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameDAO extends JpaRepository<GameModel, Long> {

    List<GameModel> findByTitleContainsIgnoreCase(String title);

}

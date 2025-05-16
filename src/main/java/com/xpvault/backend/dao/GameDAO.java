package com.xpvault.backend.dao;

import com.xpvault.backend.model.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameDAO extends JpaRepository<GameModel, Long> {

    List<GameModel> findByTitleContainsIgnoreCase(String title);

}

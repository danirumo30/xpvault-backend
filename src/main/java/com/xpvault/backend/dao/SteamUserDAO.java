package com.xpvault.backend.dao;

import com.xpvault.backend.model.SteamUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SteamUserDAO extends JpaRepository<SteamUserModel, Long> {

    Optional<SteamUserModel> findBySteamId(Long steamId);

}

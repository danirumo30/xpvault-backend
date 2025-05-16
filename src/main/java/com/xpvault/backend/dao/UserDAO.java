package com.xpvault.backend.dao;

import com.xpvault.backend.model.AppUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<AppUserModel, Long> {

    Optional<AppUserModel> findByEmail(String email);

    AppUserModel findByUsername(String username);
}

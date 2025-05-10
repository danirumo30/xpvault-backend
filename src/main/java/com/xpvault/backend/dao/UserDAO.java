package com.xpvault.backend.dao;

import com.xpvault.backend.model.AppUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<AppUserModel, Long> {

    Optional<AppUserModel> findByEmail(String email);

}

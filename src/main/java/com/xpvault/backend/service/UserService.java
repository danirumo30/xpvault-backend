package com.xpvault.backend.service;

import com.xpvault.backend.model.AppUserModel;

import java.util.List;

public interface UserService {

    List<AppUserModel> allUsers();
    AppUserModel findByUsername(String username);

}

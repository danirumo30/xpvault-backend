package com.xpvault.backend.facade.impl;

import com.xpvault.backend.facade.UserFacade;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public List<AppUserModel> allUsers() {
        return userService.allUsers();
    }
}

package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.AppUserModelToAppUserDTOConverter;
import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.facade.UserFacade;
import com.xpvault.backend.service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final AppUserModelToAppUserDTOConverter appUserModelToAppUserDTOConverter;

    @Override
    public List<AppUserDTO> allUsers() {
        return userService.allUsers()
                          .stream()
                          .map(appUserModelToAppUserDTOConverter::convert)
                          .toList();
    }

    @Override
    public AppUserDTO findByUsername(String username) {
        return appUserModelToAppUserDTOConverter.convert(userService.findByUsername(username));
    }
}

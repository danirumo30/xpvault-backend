package com.xpvault.backend.converter;

import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.model.SteamUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SteamUserModelToSteamUserDTOConverter implements Converter<SteamUserModel, SteamUserDTO> {

    @Override
    public SteamUserDTO convert(SteamUserModel source) {
        return new SteamUserDTO(
                source.getSteamId(),
                source.getNickname(),
                source.getAvatar(),
                source.getProfileUrl(),
                source.getTotalTimePlayed(),
                null
        );
    }
}

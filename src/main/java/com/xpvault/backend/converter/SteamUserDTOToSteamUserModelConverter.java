package com.xpvault.backend.converter;

import com.xpvault.backend.dto.SteamUserDTO;
import com.xpvault.backend.model.SteamUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SteamUserDTOToSteamUserModelConverter implements Converter<SteamUserDTO, SteamUserModel> {

    @Override
    public SteamUserModel convert(SteamUserDTO source) {
        return SteamUserModel.builder()
                .steamId(source.getSteamId())
                .avatar(source.getAvatar())
                .totalTimePlayed(source.getTotalTimePlayed())
                .nickname(source.getNickname())
                .profileUrl(source.getProfileUrl())
                .build();
    }
}

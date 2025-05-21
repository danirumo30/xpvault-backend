package com.xpvault.backend.converter;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerProfile;
import com.xpvault.backend.model.SteamUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SteamPlayerProfileToSteamUserModelConverter implements Converter<SteamPlayerProfile, SteamUserModel> {

    @Override
    public SteamUserModel convert(SteamPlayerProfile source) {
        return SteamUserModel.builder()
                .steamId(Long.valueOf(source.getSteamId()))
                .profileUrl(source.getProfileUrl())
                .avatar(source.getAvatarFullUrl())
                .nickname(source.getName())
                .build();
    }
}

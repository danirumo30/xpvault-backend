package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.service.SteamUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AppUserDTOToAppUserModelConverter implements Converter<AppUserDTO, AppUserModel> {

    private final SteamPlayerProfileToSteamUserModelConverter steamPlayerProfileToSteamUserModelConverter;
    private final SteamUserService steamUserService;


    @Override
    public AppUserModel convert(AppUserDTO source) {
        return AppUserModel.builder()
                           .id(source.getId())
                           .profileImage(source.getProfilePhoto() != null
                                ? Base64.getDecoder().decode(source.getProfilePhoto())
                                : null)
                           .username(source.getUsername())
                           .email(source.getEmail())
                           .password(source.getPassword())
                           .verificationCode(source.getVerificationCode())
                           .verificationExpiration(source.getVerificationExpiration())
                           .steamUser(source.getSteamUser() != null
                                           ? steamPlayerProfileToSteamUserModelConverter.convert(steamUserService.getPlayerProfile(source.getSteamUser().getSteamId()))
                                           : null
                           )
                           .build();
    }
}

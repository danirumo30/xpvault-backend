package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.model.AppUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppUserDTOToAppUserModelConverter implements Converter<AppUserDTO, AppUserModel> {

    private final SteamUserDTOToSteamUserModelConverter steamUserDTOToSteamUserModelConverter;

    @Override
    public AppUserModel convert(AppUserDTO source) {
        return AppUserModel.builder()
                           .id(source.getId())
                           .profileImage(source.getProfilePhoto())
                           .username(source.getUsername())
                           .email(source.getEmail())
                           .password(source.getPassword())
                           .verificationCode(source.getVerificationCode())
                           .verificationExpiration(source.getVerificationExpiration())
                           .steamUser(source.getSteamUser() != null
                                           ? steamUserDTOToSteamUserModelConverter.convert(source.getSteamUser())
                                           : null
                           )
                           .build();
    }
}

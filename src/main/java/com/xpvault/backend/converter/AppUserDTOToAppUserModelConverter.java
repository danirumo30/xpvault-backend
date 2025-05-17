package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppUserDTOToAppUserModelConverter implements Converter<AppUserDTO, AppUserModel> {

    @Override
    public AppUserModel convert(AppUserDTO source) {
        return AppUserModel.builder()
                           .id(source.getId())
                           .username(source.getUsername())
                           .email(source.getEmail())
                           .password(source.getPassword())
                           .verificationCode(source.getVerificationCode())
                           .verificationExpiration(source.getVerificationExpiration())
                           .steamId(source.getSteamId())
                           .build();
    }
}

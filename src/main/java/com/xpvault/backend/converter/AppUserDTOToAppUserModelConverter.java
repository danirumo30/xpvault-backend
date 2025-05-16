package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppUserDTOToAppUserModelConverter implements Converter<AppUserDTO, AppUserModel> {

    @Override
    public AppUserModel convert(AppUserDTO source) {
        return new AppUserModel(
                source.getId(),
                source.getUsername(),
                source.getEmail(),
                source.getPassword(),
                source.getVerificationCode(),
                source.getVerificationExpiration(),
                source.getSteamId()
        );
    }
}

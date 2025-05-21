package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppUserModelToAppUserDTOConverter implements Converter<AppUserModel, AppUserDTO> {

    @Override
    public AppUserDTO convert(AppUserModel source) {
        return new AppUserDTO(
                source.getId(),
                source.getUsername(),
                source.getEmail(),
                source.getPassword(),
                source.getVerificationCode(),
                source.getVerificationExpiration(),
                source.getSteamUser().getNickname()
        );
    }
}

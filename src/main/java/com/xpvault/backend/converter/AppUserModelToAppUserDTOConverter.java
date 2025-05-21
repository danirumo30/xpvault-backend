package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.model.AppUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppUserModelToAppUserDTOConverter implements Converter<AppUserModel, AppUserDTO> {

    private final SteamUserModelToSteamUserDTOConverter steamUserModelToSteamUserDTOConverter;

    @Override
    public AppUserDTO convert(AppUserModel source) {
        return new AppUserDTO(
                source.getId(),
                source.getUsername(),
                source.getEmail(),
                source.getPassword(),
                source.getVerificationCode(),
                source.getVerificationExpiration(),
                source.getSteamUser() != null
                        ? steamUserModelToSteamUserDTOConverter.convert(source.getSteamUser())
                        : null
        );
    }
}

package com.xpvault.backend.converter;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppUserModelToLoginUserDTOConverter implements Converter<AppUserModel, LoginUserDTO> {

    @Override
    public LoginUserDTO convert(AppUserModel source) {
        return new LoginUserDTO(
                source.getEmail(),
                source.getPassword()
        );
    }
}

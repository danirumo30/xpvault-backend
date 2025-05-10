package com.xpvault.backend.converter;

import com.xpvault.backend.dto.RegisterUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppUserModelToRegisterUserDTOConverter implements Converter<AppUserModel, RegisterUserDTO> {

    @Override
    public RegisterUserDTO convert(AppUserModel source) {
        return new RegisterUserDTO(
                source.getEmail(),
                source.getPassword(),
                source.getUsername()
        );
    }
}

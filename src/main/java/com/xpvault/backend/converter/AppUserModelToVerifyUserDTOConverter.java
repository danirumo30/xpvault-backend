package com.xpvault.backend.converter;

import com.xpvault.backend.dto.VerifyUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppUserModelToVerifyUserDTOConverter implements Converter<AppUserModel, VerifyUserDTO> {

    @Override
    public VerifyUserDTO convert(AppUserModel source) {
        return new VerifyUserDTO(
                source.getEmail(),
                source.getVerificationCode()
        );
    }
}

package com.xpvault.backend.converter;

import com.xpvault.backend.dto.RegisterUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserDTOToAppUserModelConverter implements Converter<RegisterUserDTO, AppUserModel> {

    @Override
    public AppUserModel convert(RegisterUserDTO source) {
        AppUserModel appUserModel = new AppUserModel();
        appUserModel.setEmail(source.getEmail());
        appUserModel.setPassword(source.getPassword());
        appUserModel.setUsername(source.getUsername());
        return appUserModel;
    }
}

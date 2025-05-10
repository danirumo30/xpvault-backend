package com.xpvault.backend.converter;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoginUserDTOToAppUserModelConverter implements Converter<LoginUserDTO, AppUserModel> {

    @Override
    public AppUserModel convert(LoginUserDTO source) {
        AppUserModel appUserModel = new AppUserModel();
        appUserModel.setEmail(source.getEmail());
        appUserModel.setPassword(source.getPassword());
        return appUserModel;
    }
}

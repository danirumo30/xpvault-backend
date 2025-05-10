package com.xpvault.backend.converter;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppUserDTOToAppUserModelConverter implements Converter<AppUserDTO, AppUserModel> {

    @Override
    public AppUserModel convert(AppUserDTO source) {
        AppUserModel appUserModel = new AppUserModel();
        appUserModel.setEmail(source.getEmail());
        appUserModel.setVerificationCode(source.getVerificationCode());
        return appUserModel;
    }
}

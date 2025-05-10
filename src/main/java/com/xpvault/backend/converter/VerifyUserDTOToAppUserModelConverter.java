package com.xpvault.backend.converter;

import com.xpvault.backend.dto.VerifyUserDTO;
import com.xpvault.backend.model.AppUserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VerifyUserDTOToAppUserModelConverter implements Converter<VerifyUserDTO, AppUserModel> {

    @Override
    public AppUserModel convert(VerifyUserDTO source) {
        AppUserModel appUserModel = new AppUserModel();
        appUserModel.setEmail(source.getEmail());
        appUserModel.setVerificationCode(source.getVerificationCode());
        return appUserModel;
    }
}

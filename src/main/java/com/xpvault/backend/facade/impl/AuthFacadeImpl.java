package com.xpvault.backend.facade.impl;

import com.xpvault.backend.converter.*;
import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.dto.RegisterUserDTO;
import com.xpvault.backend.dto.VerifyUserDTO;
import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.facade.AuthFacade;
import com.xpvault.backend.service.AuthService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class AuthFacadeImpl implements AuthFacade {

    private final AuthService authService;
    private final AppUserModelToRegisterUserDTOConverter appUserModelToRegisterUserDTOConverter;
    private final RegisterUserDTOToAppUserModelConverter registerUserDTOToAppUserModelConverter;
    private final AppUserModelToLoginUserDTOConverter appUserModelToLoginUserDTOConverter;
    private final VerifyUserDTOToAppUserModelConverter verifyUserDTOToAppUserModelConverter;
    private final AppUserDTOToAppUserModelConverter appUserDTOToAppUserModelConverter;

    @Override
    public RegisterUserDTO signUp(RegisterUserDTO registerUserDTO) {
        AppUserModel appUserModel = registerUserDTOToAppUserModelConverter.convert(registerUserDTO);
        AppUserModel saved = authService.signUp(appUserModel);
        return appUserModelToRegisterUserDTOConverter.convert(saved);
    }

    @Override
    public LoginUserDTO authenticate(LoginUserDTO loginUserDTO) {
        return appUserModelToLoginUserDTOConverter.convert(authService.authenticate(loginUserDTO));
    }

    @Override
    public void verifyUser(VerifyUserDTO verifyUserDTO) {
        authService.verifyUser(verifyUserDTOToAppUserModelConverter.convert(verifyUserDTO));
    }

    @Override
    public void resendVerificationCode(String email) {
        authService.resendVerificationCode(email);
    }

    @Override
    public void sendVerificationEmail(AppUserDTO appUserDTO) {
        authService.sendVerificationEmail(appUserDTOToAppUserModelConverter.convert(appUserDTO));
    }
}

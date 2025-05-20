package com.xpvault.backend.service;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.model.AppUserModel;

public interface AuthService {

    AppUserModel signUp(AppUserModel appUserModel);
    AppUserModel authenticate(LoginUserDTO loginUserDTO);
    void verifyUser(AppUserModel appUserModel);
    void resendVerificationCode(String email);
    void sendVerificationEmail(AppUserModel appUserModel);

}

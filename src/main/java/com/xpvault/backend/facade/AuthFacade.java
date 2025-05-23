package com.xpvault.backend.facade;

import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.dto.RegisterUserDTO;
import com.xpvault.backend.dto.VerifyUserDTO;

public interface AuthFacade {

    RegisterUserDTO signUp(RegisterUserDTO registerUserDTO);
    LoginUserDTO authenticate(LoginUserDTO loginUserDTO);
    void verifyUser(VerifyUserDTO verifyUserDTO);
    void resendVerificationCode(String email);

}

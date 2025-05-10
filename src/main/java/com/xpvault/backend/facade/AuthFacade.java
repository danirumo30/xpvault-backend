package com.xpvault.backend.facade;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.dto.LoginUserDTO;
import com.xpvault.backend.dto.RegisterUserDTO;
import com.xpvault.backend.dto.VerifyUserDTO;

public interface AuthFacade {

    /**
     * Registra a un nuevo usuario, codifica su contraseña, genera un código
     * de verificación y envía un email de verificación.
     *
     * @param registerUserDTO con los datos del usuario a registrar
     * @return Usuario creado y almacenado en la base de datos
     */
    RegisterUserDTO signUp(RegisterUserDTO registerUserDTO);

    /**
     * Autentica a un usuario por su email y contraseña.
     * Lanza error si no está verificado.
     *
     * @param loginUserDTO DTO con las credenciales del usuario
     * @return Usuario autenticado
     */
    LoginUserDTO authenticate(LoginUserDTO loginUserDTO);

    /**
     * Verifica el código enviado por el usuario y habilita su cuenta.
     *
     * @param verifyUserDTO DTO con email y código de verificación
     */
    void verifyUser(VerifyUserDTO verifyUserDTO);

    /**
     * Reenvía un nuevo código de verificación si la cuenta no está habilitada.
     *
     * @param email Email del usuario
     */
    void resendVerificationCode(String email);

    /**
     * Envía el email de verificación al usuario con código en formato HTML.
     *
     * @param appUserDTO Usuario al que se le enviará el correo
     */
    void sendVerificationEmail(AppUserDTO appUserDTO);

}

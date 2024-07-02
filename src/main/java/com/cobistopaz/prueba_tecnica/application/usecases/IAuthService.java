package com.cobistopaz.prueba_tecnica.application.usecases;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.domain.security.IAuthResponse;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.AuthUserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;

public interface IAuthService {
    public IAuthResponse ingresar(AuthUserDto usuario) throws Exception;
    public User RegistrarUsuario(RegisterUserDto nuevo) throws Exception;
}

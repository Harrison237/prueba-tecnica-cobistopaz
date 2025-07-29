package com.cobistopaz.pruebatecnica.application.usecases;

import com.cobistopaz.pruebatecnica.domain.model.User;
import com.cobistopaz.pruebatecnica.domain.security.IAuthResponse;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.dto.AuthUserDto;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;

public interface IAuthService {
    public IAuthResponse ingresar(AuthUserDto usuario) throws Exception;
    public User RegistrarUsuario(RegisterUserDto nuevo) throws Exception;
}

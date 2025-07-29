package com.cobistopaz.pruebatecnica.application.usecases;

import java.util.List;

import com.cobistopaz.pruebatecnica.domain.model.User;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.dto.UpdateUserDto;

public interface IUsersService {
    User crearUsuario(RegisterUserDto user) throws Exception;
    User buscarPorId(String id) throws Exception;
    List<User> buscarTodos() throws Exception;
    User modificar(String id, UpdateUserDto user) throws Exception;
    void eliminar(String id, String contrasena) throws Exception;
    User desdeDtoAUser(RegisterUserDto dto);
}

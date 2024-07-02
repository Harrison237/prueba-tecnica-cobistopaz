package com.cobistopaz.prueba_tecnica.application.usecases;

import java.util.List;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UpdateUserDto;

public interface IUsersService {
    User crearUsuario(RegisterUserDto user) throws Exception;
    User buscarPorId(String id) throws Exception;
    List<User> buscarTodos() throws Exception;
    User modificar(String id, UpdateUserDto user) throws Exception;
    void eliminar(String id, String contrasena) throws Exception;
    User desdeDtoAUser(RegisterUserDto dto);
}

package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.services;

import java.util.List;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UserDto;

public interface IUsersService {
    User crearUsuario(String nombreUsuario, String contrasena, String roles) throws Exception;
    User buscarPorId(String id) throws Exception;
    List<User> buscarTodos() throws Exception;
    User modificar(String id, User user) throws Exception;
    void eliminar(String id) throws Exception;
    User desdeDtoAUser(UserDto dto);
}

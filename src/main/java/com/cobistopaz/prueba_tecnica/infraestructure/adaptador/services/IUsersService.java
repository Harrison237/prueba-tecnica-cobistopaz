package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.services;

import java.util.List;

import com.cobistopaz.prueba_tecnica.domain.model.User;

public interface IUsersService {
    User crearUsuario(String nombreUsuario, String contrasena, String roles);
    User buscarPorId(String id);
    List<User> buscarTodos();
    User modificar(String id, User user);
    void eliminar(String id);
}

package com.cobistopaz.prueba_tecnica.application.ports;

import java.util.List;

import com.cobistopaz.prueba_tecnica.domain.model.User;

public interface UsersPort {
    User guardar(User user) throws Exception;
    User consultarPorId(String id) throws Exception;
    User consultarPorNombreUsuario(String nombreUsuario) throws Exception;
    List<User> consultarTodos() throws Exception;
    User modificar(String id, User user) throws Exception;
    void eliminar(User user) throws Exception;
}

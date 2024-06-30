package com.cobistopaz.prueba_tecnica.application.ports;

import java.util.List;

import com.cobistopaz.prueba_tecnica.domain.model.User;

public interface UsersPort {
    User guardar(User user);
    User consultarPorId(String id);
    List<User> consultarTodos();
    User modificar(String id, User user);
    void eliminar(User user);
}

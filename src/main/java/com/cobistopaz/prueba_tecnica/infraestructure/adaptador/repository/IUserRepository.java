package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository;

import java.util.List;

import com.cobistopaz.prueba_tecnica.domain.model.User;

public interface IUserRepository {
    User guardar(User user);
    User consultarPorId(String id);
    List<User> consultarTodos();
    User modificar(String id, User user);
    void eliminar(User user);
}

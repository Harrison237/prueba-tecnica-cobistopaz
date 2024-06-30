package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.IUserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class UserRepositoryJpaMysql implements IUserRepository {

    @PersistenceContext
    private EntityManager usersManager;

    @Override
    public User guardar(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }

    @Override
    public User consultarPorId(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'encontrarPorId'");
    }

    @Override
    public List<User> consultarTodos() {
        throw new UnsupportedOperationException("Unimplemented method 'todos'");
    }

    @Override
    public User modificar(String id, User user) {
        throw new UnsupportedOperationException("Unimplemented method 'modificar'");
    }

    @Override
    public void eliminar(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }
    
}

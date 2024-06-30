package com.cobistopaz.prueba_tecnica.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.IUserRepository;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.services.IUsersService;

public class UsersServiceImpl implements IUsersService {

    @Autowired
    IUserRepository usersRepository;

    @Override
    public User crearUsuario(String nombreUsuario, String contrasena, String roles) {
        return this.usersRepository.guardar(null);
    }

    @Override
    public User buscarPorId(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    @Override
    public List<User> buscarTodos() {
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodos'");
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

package com.cobistopaz.prueba_tecnica.application.services;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.mappers.IUserMapper;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.IUserRepository;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.services.IUsersService;

@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private IUserRepository usersRepository;

    @Override
    public User crearUsuario(String nombreUsuario, String contrasena, String roles) {
        return this.usersRepository.guardar(null);
    }

    @Override
    public User buscarPorId(String id) {
        return this.usersRepository.consultarPorId(id);
    }

    @Override
    public List<User> buscarTodos() {
        return this.usersRepository.consultarTodos();
    }

    @Override
    public User modificar(String id, User user) {
        throw new UnsupportedOperationException("Unimplemented method 'modificar'");
    }

    @Override
    public void eliminar(String id) throws BadRequestException {
        User usuario = usersRepository.consultarPorId(id);

        this.usersRepository.eliminar(usuario);
    }

    @Override
    public User desdeDtoAUser(UserDto dto) {
        return IUserMapper.mapper.desdeDtoADomain(dto);
    }

}

package com.cobistopaz.prueba_tecnica.application.services;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cobistopaz.prueba_tecnica.application.ports.UsersPort;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.mappers.IUserMapper;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.entity.UserEntity;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.services.IUsersService;

@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private UsersPort usersRepository;
    private PasswordEncoder codificador;

    public UsersServiceImpl() {
        codificador = new BCryptPasswordEncoder();
    }

    @Override
    public User crearUsuario(User user) throws Exception {
        user.setId(System.currentTimeMillis()+"");
        user.setContrasena(codificador.encode(user.getContrasena()));

        return usersRepository.guardar(user);
    }

    @Override
    public User buscarPorId(String id) {
        return usersRepository.consultarPorId(id);
    }

    @Override
    public List<User> buscarTodos() {
        return usersRepository.consultarTodos();
    }

    @Override
    public User modificar(String id, User user) {
        User actual = usersRepository.consultarPorId(id);
        boolean cambiado = false;

        //Lógica para asignar valores del objeto enviado y verificar si cambió en algo con el objeto actualmente persistido
        //Se puede mejorar mediante los métodos de la clase Object, pero para efectos prácticos y al ser solo 3 campos los que pueden cambiar entonces se deja así.
        if (user.getNombreUsuario() != null && !actual.getNombreUsuario().equals(user.getNombreUsuario())) {
            cambiado = true;
            actual.setNombreUsuario(user.getNombreUsuario());
        }
        if (user.getRoles() != null && !actual.getRoles().equals(user.getRoles())) {
            cambiado = true;
            actual.setRoles(user.getRoles());
        }
        if (user.getContrasena() != null && !codificador.matches(user.getContrasena(), actual.getContrasena())) {
            cambiado = true;
            actual.setContrasena(codificador.encode(user.getContrasena()));
        }

        //Si no cambió nada, se retorna el objeto actual
        if (!cambiado) {
            return actual;
        }

        return usersRepository.modificar(id, actual);
    }

    @Override
    public void eliminar(String id) throws BadRequestException {
        User usuario = usersRepository.consultarPorId(id);

        usersRepository.eliminar(usuario);
    }

    @Override
    public User desdeDtoAUser(UserDto dto) {
        return IUserMapper.mapper.desdeDtoADomain(dto);
    }

}

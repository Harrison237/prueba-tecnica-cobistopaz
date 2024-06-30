package com.cobistopaz.prueba_tecnica.application.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cobistopaz.prueba_tecnica.application.ports.UsersPort;
import com.cobistopaz.prueba_tecnica.domain.exceptions.UsuarioExistenteException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.UsuarioNoEncontradoException;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.mappers.IUserMapper;
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
        try {
            user.setId(System.currentTimeMillis() + "");
            user.setContrasena(codificador.encode(user.getContrasena()));

            return usersRepository.guardar(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsuarioExistenteException("Ya existe un usuario con el nombre: " + user.getNombreUsuario());
        }
    }

    @Override
    public User buscarPorId(String id) throws Exception {
        try {
            return usersRepository.consultarPorId(id);
        } catch (NoSuchElementException e) {
            throw new UsuarioNoEncontradoException("No existe un usuario con el id: " + id);
        }
    }

    @Override
    public List<User> buscarTodos() throws Exception {
        return usersRepository.consultarTodos();
    }

    @Override
    public User modificar(String id, User user) throws Exception {
        try {
            User actual = usersRepository.consultarPorId(id);
            boolean cambiado = false;

            // Lógica para asignar valores del objeto enviado y verificar si cambió en algo
            // con el objeto actualmente persistido
            // Se puede mejorar mediante los métodos de la clase Object, pero para efectos
            // prácticos y al ser solo 3 campos los que pueden cambiar entonces se deja así.
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

            // Si no cambió nada, se retorna el objeto actual
            if (!cambiado) {
                return actual;
            }

            return usersRepository.modificar(id, actual);

        } catch (NoSuchElementException e) {
            throw new UsuarioNoEncontradoException("No existe un usuario con el id: " + id);
        }
    }

    @Override
    public void eliminar(String id) throws Exception {
        try {
            User usuario = usersRepository.consultarPorId(id);
    
            usersRepository.eliminar(usuario);
        } catch (NoSuchElementException e) {
            throw new UsuarioNoEncontradoException("No existe un usuario con el id: " + id);
        }
    }

    @Override
    public User desdeDtoAUser(UserDto dto) {
        return IUserMapper.mapper.desdeDtoADomain(dto);
    }

}

package com.cobistopaz.prueba_tecnica.application.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cobistopaz.prueba_tecnica.application.ports.UsersPort;
import com.cobistopaz.prueba_tecnica.application.usecases.IUsersService;
import com.cobistopaz.prueba_tecnica.domain.exceptions.ContrasenaException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.PeticionVaciaException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.UsuarioExistenteException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.UsuarioNoEncontradoException;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.mappers.IUserMapper;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UpdateUserDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private UsersPort usersRepository;
    private PasswordEncoder codificador;

    @Override
    public User crearUsuario(RegisterUserDto user) throws Exception {
        try {
            User registrado = usersRepository.guardar(
                    User.builder()
                            .id(System.currentTimeMillis() + "")
                            .nombreUsuario(user.getNombreUsuario())
                            .contrasena(codificador.encode(user.getContrasena()))
                            .roles(user.getRoles())
                            .build());
            // Al retornar el usuario creado, en la petición no debería ir la contraseña
            // encriptada
            registrado.setContrasena("");
            return registrado;
        } catch (DataIntegrityViolationException e) {
            throw new UsuarioExistenteException("Ya existe un usuario con el nombre: " + user.getNombreUsuario());
        }
    }

    @Override
    public User buscarPorId(String id) throws Exception {
        try {
            User encontrado = usersRepository.consultarPorId(id);
            encontrado.setContrasena("");

            return encontrado;
        } catch (NoSuchElementException e) {
            throw new UsuarioNoEncontradoException("No existe un usuario con el id: " + id);
        }
    }

    @Override
    public List<User> buscarTodos() throws Exception {
        return usersRepository.consultarTodos().stream().map(user -> {
            user.setContrasena("");
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    public User modificar(String id, UpdateUserDto user) throws Exception {
        try {
            //Validación manual en caso de que no se envíe nada en el cuerpo de la petición
            if (user.getNombreUsuario() == null && user.getContrasenaNueva() == null && user.getContrasenaActual() == null && user.getRoles() == null) {
                throw new PeticionVaciaException("Debe enviar al menos un campo para actualizar.");
            }

            User actual = usersRepository.consultarPorId(id);
            boolean cambiado = false;

            // Lógica para asignar valores del objeto enviado y verificar si cambió en algo
            // con el objeto actualmente persistido.
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
            if (user.getContrasenaActual() == null && user.getContrasenaNueva() != null) {
                throw new ContrasenaException("Debe ingresar la contraseña actual.");
            }
            if (user.getContrasenaActual() != null && user.getContrasenaNueva() != null) {
                if (user.getContrasenaActual().equals(user.getContrasenaNueva())) {
                    throw new ContrasenaException("Los campos de contraseña no deben ser iguales.");
                }
                if (!codificador.matches(user.getContrasenaActual(), actual.getContrasena())) {
                    throw new ContrasenaException("La contraseña ingresada no coincide con la contraseña registrada previamente.");
                }
                cambiado = true;
                actual.setContrasena(codificador.encode(user.getContrasenaNueva()));
            }

            // Si se encontró algún cambio se envía a la base de datos
            if (cambiado) {
                actual = usersRepository.modificar(id, actual);
            }

            // Se oculta la contraseña en la respuesta
            actual.setContrasena("");
            return actual;

        } catch (NoSuchElementException e) {
            throw new UsuarioNoEncontradoException("No existe un usuario con el id: " + id);
        }
    }

    @Override
    public void eliminar(String id, String contrasena) throws Exception {
        try {
            if (contrasena == null) {
                throw new ContrasenaException("Debe ingresar la contraseña para poder eliminar el usuario.");
            }

            User usuario = usersRepository.consultarPorId(id);

            if (!codificador.matches(contrasena, usuario.getContrasena())) {
                throw new ContrasenaException("La contraseña ingresada no coincide con la contraseña registrada previamente. Operación cancelada.");
            }

            usersRepository.eliminar(usuario);
        } catch (NoSuchElementException e) {
            throw new UsuarioNoEncontradoException("No existe un usuario con el id: " + id);
        }
    }

    @Override
    public User desdeDtoAUser(RegisterUserDto dto) {
        return IUserMapper.mapper.desdeDtoADomain(dto);
    }

}

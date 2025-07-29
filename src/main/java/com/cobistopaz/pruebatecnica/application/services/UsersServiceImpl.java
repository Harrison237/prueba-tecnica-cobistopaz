package com.cobistopaz.pruebatecnica.application.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cobistopaz.pruebatecnica.application.ports.UsersPort;
import com.cobistopaz.pruebatecnica.application.usecases.IUsersService;
import com.cobistopaz.pruebatecnica.domain.exceptions.ContrasenaException;
import com.cobistopaz.pruebatecnica.domain.exceptions.PeticionVaciaException;
import com.cobistopaz.pruebatecnica.domain.exceptions.UsuarioExistenteException;
import com.cobistopaz.pruebatecnica.domain.exceptions.UsuarioNoEncontradoException;
import com.cobistopaz.pruebatecnica.domain.model.User;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.mappers.IUserMapper;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.dto.UpdateUserDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {

    private UsersPort usersRepository;
    private PasswordEncoder codificador;

    @Override
    public User crearUsuario(RegisterUserDto user) throws Exception {
        try {
            User registrar = User.builder()
                    .id(System.currentTimeMillis() + "")
                    .nombreUsuario(user.getNombreUsuario())
                    .contrasena(codificador.encode(user.getContrasena()))
                    .roles(user.getRoles())
                    .build();
            User registrado = usersRepository.guardar(registrar);
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
        }).toList();
    }

    @Override
    public User modificar(String id, UpdateUserDto user) throws Exception {
        try {
            validarPeticion(user);

            User actual = usersRepository.consultarPorId(id);
            boolean cambiado = actualizarCampos(actual, user);

            if (cambiado) {
                actual = usersRepository.modificar(id, actual);
            }

            actual.setContrasena("");
            return actual;

        } catch (NoSuchElementException e) {
            throw new UsuarioNoEncontradoException("No existe un usuario con el id: " + id);
        }
    }

    private void validarPeticion(UpdateUserDto user) throws PeticionVaciaException {
        if (user.getNombreUsuario() == null && user.getContrasenaNueva() == null
                && user.getContrasenaActual() == null && user.getRoles() == null) {
            throw new PeticionVaciaException("Debe enviar al menos un campo para actualizar.");
        }
    }

    private boolean actualizarCampos(User actual, UpdateUserDto user) throws ContrasenaException {
        boolean cambiado = false;

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
            validarYActualizarContrasena(actual, user);
            cambiado = true;
        }
        return cambiado;
    }

    private void validarYActualizarContrasena(User actual, UpdateUserDto user) throws ContrasenaException {
        if (user.getContrasenaActual().equals(user.getContrasenaNueva())) {
            throw new ContrasenaException("Los campos de contraseña no deben ser iguales.");
        }
        if (!codificador.matches(user.getContrasenaActual(), actual.getContrasena())) {
            throw new ContrasenaException(
                    "La contraseña ingresada no coincide con la contraseña registrada previamente.");
        }
        actual.setContrasena(codificador.encode(user.getContrasenaNueva()));
    }

    @Override
    public void eliminar(String id, String contrasena) throws Exception {
        try {
            if (contrasena == null) {
                throw new ContrasenaException("Debe ingresar la contraseña para poder eliminar el usuario.");
            }

            User usuario = usersRepository.consultarPorId(id);

            if (!codificador.matches(contrasena, usuario.getContrasena())) {
                throw new ContrasenaException(
                        "La contraseña ingresada no coincide con la contraseña registrada previamente. Operación cancelada.");
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

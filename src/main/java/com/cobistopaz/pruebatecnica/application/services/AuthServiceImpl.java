package com.cobistopaz.pruebatecnica.application.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cobistopaz.pruebatecnica.application.ports.UsersPort;
import com.cobistopaz.pruebatecnica.application.usecases.IAuthService;
import com.cobistopaz.pruebatecnica.domain.exceptions.UsuarioExistenteException;
import com.cobistopaz.pruebatecnica.domain.exceptions.UsuarioNoEncontradoException;
import com.cobistopaz.pruebatecnica.domain.model.User;
import com.cobistopaz.pruebatecnica.domain.security.IAuthResponse;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.dto.AuthUserDto;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.dto.RegisterAuthUserDto;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;
import com.cobistopaz.pruebatecnica.infraestructure.api.security.IAuthManager;
import com.cobistopaz.pruebatecnica.infraestructure.api.security.jwt.JwtResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private UsersPort usersManager;
    private PasswordEncoder codificador;
    private IAuthManager tokenManager;
    private AuthenticationManager authManager;

    @Override
    public IAuthResponse ingresar(AuthUserDto usuario) throws Exception {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getNombreUsuario(), usuario.getContrasena()));
            User encontrado = usersManager.consultarPorNombreUsuario(usuario.getNombreUsuario());

            UserDetails details = new RegisterAuthUserDto(encontrado.getNombreUsuario(), encontrado.getContrasena(),
                    encontrado.getRoles());
            String token = tokenManager.generarToken(details);

            return JwtResponse.builder().token(token).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsuarioNoEncontradoException("Las credenciales ingresadas no son validas");
        }
    }

    @Override
    public User RegistrarUsuario(RegisterUserDto nuevo) throws Exception {
        try {
            User registrado = usersManager.guardar(
                    User.builder()
                            .id(System.currentTimeMillis() + "")
                            .nombreUsuario(nuevo.getNombreUsuario())
                            .contrasena(codificador.encode(nuevo.getContrasena()))
                            .roles(nuevo.getRoles())
                            .build());
            // Al retornar el usuario creado, en la petición no debería ir la contraseña
            // encriptada
            registrado.setContrasena("");
            return registrado;
        } catch (DataIntegrityViolationException e) {
            throw new UsuarioExistenteException("Ya existe un usuario con el nombre: " + nuevo.getNombreUsuario());
        }
    }

}

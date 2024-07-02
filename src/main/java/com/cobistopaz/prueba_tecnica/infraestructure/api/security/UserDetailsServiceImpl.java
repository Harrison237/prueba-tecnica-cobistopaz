package com.cobistopaz.prueba_tecnica.infraestructure.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cobistopaz.prueba_tecnica.application.ports.UsersPort;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterAuthUserDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersPort usersManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = usersManager.consultarPorNombreUsuario(username);

            return new RegisterAuthUserDto(user.getNombreUsuario(), user.getContrasena(), user.getRoles());
        } catch (Exception e) {
            throw new UsernameNotFoundException("No se encontró un usuario con el nombre: "+username);
        }
    }

}

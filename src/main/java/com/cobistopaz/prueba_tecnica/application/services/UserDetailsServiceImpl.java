package com.cobistopaz.prueba_tecnica.application.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cobistopaz.prueba_tecnica.application.implementations.UserDetailsImpl;
import com.cobistopaz.prueba_tecnica.domain.model.Credenciales;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.UserPersistenceAdapter;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserPersistenceAdapter usersManager;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = usersManager.consultarPorNombreUsuario(username);

            return new UserDetailsImpl(new Credenciales(user.getNombreUsuario(), user.getContrasena()));
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("No se encontró un usuario con el nombre: "+username);
        }
    }
}

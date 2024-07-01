package com.cobistopaz.prueba_tecnica.application.implementations;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cobistopaz.prueba_tecnica.domain.model.Credenciales;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Credenciales credenciales;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return credenciales.getContrasena();
    }

    @Override
    public String getUsername() {
        return credenciales.getNombreUsuario();
    }

}

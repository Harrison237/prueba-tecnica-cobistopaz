package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAuthUserDto implements UserDetails {
    private String nombreUsuario;
    private String contrasena;
    private String roles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles));
    }
    @Override
    public String getPassword() {
        return contrasena;
    }
    @Override
    public String getUsername() {
        return nombreUsuario;
    }
}

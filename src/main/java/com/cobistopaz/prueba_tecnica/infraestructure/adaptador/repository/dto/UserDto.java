package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String nombreUsuario;
    private String contrasena;
    private String roles;
}

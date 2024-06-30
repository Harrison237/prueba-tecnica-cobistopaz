package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String nombreUsuario;
    private String contrasena;
    private String roles;
}

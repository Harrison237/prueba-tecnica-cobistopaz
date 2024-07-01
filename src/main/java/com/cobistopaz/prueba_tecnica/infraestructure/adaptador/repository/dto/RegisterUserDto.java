package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterUserDto {
    private String nombreUsuario;
    private String contrasena;
    private String roles;
}

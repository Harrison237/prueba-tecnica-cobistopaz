package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private String nombreUsuario;
    private String contrasenaActual;
    private String contrasenaNueva;
    private String roles;
}

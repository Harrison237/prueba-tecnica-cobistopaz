package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Clase utilizada para realizar las operaciones de actualización de usuario.")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    @Schema(description = "Nombre de usuario nuevo para el registro guardado en la base de datos.")
    private String nombreUsuario;

    @Schema(description = "Contraseña actual del usuario en caso de que se quiera cambiar por una nueva.")
    private String contrasenaActual;

    @Schema(description = "Contraseña nueva para el usuario.")
    private String contrasenaNueva;

    @Schema(description = "Nuevos roles para el usuario.")
    private String roles;
}

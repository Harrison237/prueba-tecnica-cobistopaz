package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Clase utilizada para recibir las credenciales de ingreso al sistema.")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto {
    @Schema(description = "Nombre de algún usuario que debe estar registrado en la base de datos. No puede estar en blanco.")
    @NotNull(message = "El nombre de usuario es obligatorio")
    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    private String nombreUsuario;

    @Schema(description = "Contraseña del usuario registrado en la base de datos. No puede estar en blanco.")
    @NotNull(message = "La contraseña es obligatoria")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    private String contrasena;
}

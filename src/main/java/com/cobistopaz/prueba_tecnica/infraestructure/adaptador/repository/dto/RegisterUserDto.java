package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "RegisterUserDto", description = "Se usa como entrada de datos para generar un usuario en la base de datos.")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterUserDto {
    @Valid

    @Schema(name = "nombreUsuario", description = "Nombre del usuario que será guardado en la base de datos.")
    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @NotNull(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @Schema(name = "contrasena", description = "Contraseña que será guardada en la base de datos. Al momento de registrarse será encriptada y no se retornará en la respuesta al usuario.")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @NotNull(message = "La contraseña es obligatoria")
    private String contrasena;

    @Schema(name = "roles", description = "Campo para asignar roles al usuario, se tiene pensado que sea un texto separado por comas, aunque una mejor implementación sería mediante una tabla de roles.")
    @NotBlank(message = "Los roles no pueden estar en blanco")
    @NotNull(message = "Los roles son obligatorios")
    private String roles;
}

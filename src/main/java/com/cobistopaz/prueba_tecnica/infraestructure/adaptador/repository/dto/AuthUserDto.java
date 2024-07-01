package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto {
    @NotNull(message = "El nombre de usuario es obligatorio")
    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    private String nombreUsuario;

    @NotNull(message = "La contraseña es obligatoria")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    private String contrasena;
}

package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterUserDto {
    @Valid

    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @NotNull(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @NotNull(message = "La contraseña es obligatoria")
    private String contrasena;
    
    @NotBlank(message = "Los roles no pueden estar en blanco")
    @NotNull(message = "Los roles son obligatorios")
    private String roles;
}

package com.cobistopaz.prueba_tecnica.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credenciales {
    private String nombreUsuario;
    private String contrasena;
}

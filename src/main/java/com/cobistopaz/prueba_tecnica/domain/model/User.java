package com.cobistopaz.prueba_tecnica.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private String id;
    private String nombreUsuario;
    private String contrasena;
    private String roles;
}

package com.cobistopaz.prueba_tecnica.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Clase del dominio que se retorna en todas las peticiones que tengan que ver con usuarios.")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Schema(description = "ID del usuario guardado en la base de datos.")
    private String id;

    @Schema(description = "Nombre de usuario del registro en la base de datos.")
    private String nombreUsuario;

    @Schema(description = "Contraseña del usuario en la base de datos. Por lo general se retorna en blanco.")
    private String contrasena;

    @Schema(description = "Roles del usuario en la base de datos.")
    private String roles;
}

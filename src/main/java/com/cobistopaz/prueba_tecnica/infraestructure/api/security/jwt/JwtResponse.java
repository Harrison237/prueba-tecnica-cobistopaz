package com.cobistopaz.prueba_tecnica.infraestructure.api.security.jwt;

import com.cobistopaz.prueba_tecnica.domain.security.IAuthResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Clase con la que se retorna el token de autorización.")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements IAuthResponse {
    @Schema(description = "Token de autorización otorgado tras validar correctamente las credenciales del usuario.")
    private String token;
}

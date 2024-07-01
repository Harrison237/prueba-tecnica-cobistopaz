package com.cobistopaz.prueba_tecnica.infraestructure.api.security.jwt;

import com.cobistopaz.prueba_tecnica.domain.security.IAuthResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements IAuthResponse {
    private String token;
}

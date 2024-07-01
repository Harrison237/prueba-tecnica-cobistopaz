package com.cobistopaz.prueba_tecnica.domain.security;

import com.cobistopaz.prueba_tecnica.domain.model.Credenciales;

public interface IAuthManager {
    String generarToken(Credenciales credenciales) throws Exception;
    boolean validarToken(String token) throws Exception;
    Credenciales extraerData(String token) throws Exception;
}

package com.cobistopaz.pruebatecnica.infraestructure.api.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthManager {
    String generarToken(UserDetails details) throws Exception;
    boolean validarToken(String token, UserDetails details) throws Exception;
    String extraerUsername(String token) throws Exception;
}

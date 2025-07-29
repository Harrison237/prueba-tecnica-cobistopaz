package com.cobistopaz.pruebatecnica.domain.exceptions;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String mensaje) {
        super(mensaje);
    }
}

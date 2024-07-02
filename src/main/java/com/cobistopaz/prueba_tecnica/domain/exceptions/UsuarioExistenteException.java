package com.cobistopaz.prueba_tecnica.domain.exceptions;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String mensaje) {
        super(mensaje);
    }
}

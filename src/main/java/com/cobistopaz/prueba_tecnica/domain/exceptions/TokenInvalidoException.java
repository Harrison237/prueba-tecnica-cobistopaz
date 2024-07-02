package com.cobistopaz.prueba_tecnica.domain.exceptions;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String mensaje) {
        super(mensaje);
    }
}

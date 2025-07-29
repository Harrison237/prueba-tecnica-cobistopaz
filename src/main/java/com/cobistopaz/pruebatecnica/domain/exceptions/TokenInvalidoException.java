package com.cobistopaz.pruebatecnica.domain.exceptions;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String mensaje) {
        super(mensaje);
    }
}

package com.cobistopaz.prueba_tecnica.domain.exceptions;

public class NoAutorizadoException extends RuntimeException {
    public NoAutorizadoException(String mensaje) {
        super(mensaje);
    }
}

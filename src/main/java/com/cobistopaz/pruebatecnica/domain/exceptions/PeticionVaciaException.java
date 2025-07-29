package com.cobistopaz.pruebatecnica.domain.exceptions;

public class PeticionVaciaException extends RuntimeException {
    public PeticionVaciaException(String mensaje) {
        super(mensaje);
    }
}

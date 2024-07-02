package com.cobistopaz.prueba_tecnica.domain.exceptions;

public class PeticionVaciaException extends RuntimeException {
    public PeticionVaciaException(String mensaje) {
        super(mensaje);
    }
}

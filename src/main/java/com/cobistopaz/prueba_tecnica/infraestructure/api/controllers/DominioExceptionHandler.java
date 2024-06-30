package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cobistopaz.prueba_tecnica.domain.exceptions.NoAutorizadoException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.UsuarioExistenteException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.UsuarioNoEncontradoException;
import com.cobistopaz.prueba_tecnica.domain.web.RespuestaHttp;
import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;

@RestControllerAdvice
public class DominioExceptionHandler {

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<RespuestaHttp> manejadorUsuarioNoEncontrado(UsuarioNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<RespuestaHttp> manejadorUsuarioExistente(UsuarioExistenteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoAutorizadoException.class)
    public ResponseEntity<RespuestaHttp> manejadorUsuarioNoAutorizado(NoAutorizadoException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }
}

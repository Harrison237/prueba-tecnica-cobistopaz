package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cobistopaz.prueba_tecnica.domain.exceptions.ContrasenaException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.NoAutorizadoException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.PeticionVaciaException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.TokenInvalidoException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.UsuarioExistenteException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.UsuarioNoEncontradoException;
import com.cobistopaz.prueba_tecnica.domain.web.RespuestaHttp;
import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class DominioExceptionHandler {

    @Autowired
    private Gson gson;

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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<RespuestaHttp> manejadorUsuarioNoEncontrado(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<RespuestaHttp> manejadorTokenInvalido(TokenInvalidoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ContrasenaException.class)
    public ResponseEntity<RespuestaHttp> manejadorErroresContrasena(ContrasenaException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaHttp> manejadorClaseNoValida(MethodArgumentNotValidException e) {
        System.out.println(e.getFieldValue("contrasenaActual"));
        List<String> errores = e.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                RespuestaHttpFactory.respuestaError(
                        "Se encontraron los siguientes errores de validación: " + errores.toString(),
                        HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(PeticionVaciaException.class)
    public ResponseEntity<RespuestaHttp> manejadorPeticionVacia(PeticionVaciaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                RespuestaHttpFactory.respuestaError(
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST.value()));
    }
}

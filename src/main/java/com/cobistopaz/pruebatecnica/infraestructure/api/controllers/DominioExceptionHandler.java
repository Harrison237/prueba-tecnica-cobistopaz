package com.cobistopaz.pruebatecnica.infraestructure.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cobistopaz.pruebatecnica.domain.exceptions.ContrasenaException;
import com.cobistopaz.pruebatecnica.domain.exceptions.NoAutorizadoException;
import com.cobistopaz.pruebatecnica.domain.exceptions.PeticionVaciaException;
import com.cobistopaz.pruebatecnica.domain.exceptions.TokenInvalidoException;
import com.cobistopaz.pruebatecnica.domain.exceptions.UsuarioExistenteException;
import com.cobistopaz.pruebatecnica.domain.exceptions.UsuarioNoEncontradoException;
import com.cobistopaz.pruebatecnica.domain.web.RespuestaHttp;
import com.cobistopaz.pruebatecnica.domain.web.factorias.RespuestaHttpFactory;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class DominioExceptionHandler {

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<RespuestaHttp<String>> manejadorUsuarioNoEncontrado(UsuarioNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<RespuestaHttp<String>> manejadorUsuarioExistente(UsuarioExistenteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoAutorizadoException.class)
    public ResponseEntity<RespuestaHttp<String>> manejadorUsuarioNoAutorizado(NoAutorizadoException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<RespuestaHttp<String>> manejadorUsuarioNoEncontrado(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<RespuestaHttp<String>> manejadorTokenInvalido(TokenInvalidoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ContrasenaException.class)
    public ResponseEntity<RespuestaHttp<String>> manejadorErroresContrasena(ContrasenaException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                RespuestaHttpFactory.respuestaError(e.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaHttp<String>> manejadorClaseNoValida(MethodArgumentNotValidException e) {
        System.out.println(e.getFieldValue("contrasenaActual"));
        List<String> errores = e.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                RespuestaHttpFactory.respuestaError(
                        "Se encontraron los siguientes errores de validación: " + errores.toString(),
                        HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(PeticionVaciaException.class)
    public ResponseEntity<RespuestaHttp<String>> manejadorPeticionVacia(PeticionVaciaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                RespuestaHttpFactory.respuestaError(
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST.value()));
    }
}

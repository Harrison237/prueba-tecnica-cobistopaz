package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cobistopaz.prueba_tecnica.domain.model.Credenciales;
import com.cobistopaz.prueba_tecnica.domain.web.RespuestaHttp;
import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp> getToken(@RequestBody Credenciales credenciales) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa(HttpStatus.ACCEPTED.value(), "ok"));
    }
    
}

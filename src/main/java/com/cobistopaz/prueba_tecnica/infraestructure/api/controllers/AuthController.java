package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cobistopaz.prueba_tecnica.domain.web.RespuestaHttp;
import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    @PostMapping("login")
    public ResponseEntity<RespuestaHttp> getToken(@RequestHeader("Authorization") String header) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa(HttpStatus.ACCEPTED.value(), header));
    }
    
}

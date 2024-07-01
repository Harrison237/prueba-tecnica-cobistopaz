package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cobistopaz.prueba_tecnica.application.usecases.IAuthService;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.domain.security.IAuthResponse;
import com.cobistopaz.prueba_tecnica.domain.web.RespuestaHttp;
import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.AuthUserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp> getToken(@RequestBody AuthUserDto credenciales) throws Exception {
        IAuthResponse respuesta = authService.ingresar(credenciales);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa("Se ha autenticado éxitosamente.", HttpStatus.ACCEPTED.value(),
                        respuesta));
    }

    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp> registrarUsuario(@RequestBody RegisterUserDto nuevo) throws Exception {
        User creado = authService.RegistrarUsuario(nuevo);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa(
                        "Usuario registrado exitosamente. Ahora puede usar sus nuevas credenciales para iniciar sesión. Recuerde que una vez agregado el token de autorización, los métodos del auth-controller quedarán inhabilitados hasta que el token sea removido de la petición.",
                        HttpStatus.ACCEPTED.value(), creado));
    }
}

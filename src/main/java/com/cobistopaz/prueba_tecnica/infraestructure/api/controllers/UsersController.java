package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.domain.web.RespuestaHttp;
import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.mappers.IUserMapper;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.services.IUsersService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "api/users")
public class UsersController {

    @Autowired
    private IUsersService usersService;

    @GetMapping(value = "todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp> getTodos() throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa(HttpStatus.ACCEPTED.value(), usersService.buscarTodos()));
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp> getUsuarioPorId(@PathVariable String id) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa(HttpStatus.ACCEPTED.value(), usersService.buscarPorId(id)));
    }

    @PostMapping(value = "crear", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp> crearNuevoUsuario(@RequestBody UserDto nuevoUsuario) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa(
                        HttpStatus.ACCEPTED.value(),
                        usersService.crearUsuario(usersService.desdeDtoAUser(nuevoUsuario))));
    }

    @PatchMapping(value = "actualizar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp> actualizarUsuario(@RequestBody UserDto usuario, @PathVariable String id)
            throws Exception {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa(
                        HttpStatus.ACCEPTED.value(),
                        usersService.modificar(id, IUserMapper.mapper.desdeDtoADomain(usuario))));
    }

    @DeleteMapping(value = "eliminar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp> eliminarUsuario(@PathVariable String id) throws Exception {
        usersService.eliminar(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa("Usuario eliminado exitosamente", HttpStatus.ACCEPTED.value(),
                        ""));
    }

}

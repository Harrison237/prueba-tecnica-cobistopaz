package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.mappers.IUserMapper;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.services.IUsersService;

import org.springframework.http.MediaType;

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
    public List<User> getTodos() throws Exception {
        return usersService.buscarTodos();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUsuarioPorId(@PathVariable String id) throws Exception {
        return usersService.buscarPorId(id);
    }

    @PostMapping(value = "crear", produces = MediaType.APPLICATION_JSON_VALUE)
    public User crearNuevoUsuario(@RequestBody UserDto nuevoUsuario) throws Exception {
        return usersService.crearUsuario(usersService.desdeDtoAUser(nuevoUsuario));
    }

    @PatchMapping(value = "actualizar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User actualizarUsuario(@RequestBody UserDto usuario, @PathVariable String id) throws Exception {
        return usersService.modificar(id, IUserMapper.mapper.desdeDtoADomain(usuario));
    }

    @DeleteMapping(value = "eliminar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> eliminarUsuario(@PathVariable String id) throws Exception {
        Map<String, Object> result = new HashMap<>();

        usersService.eliminar(id);
        String mensaje = "Usuario eliminado exitosamente";
        result.put("mensaje", mensaje);

        return result;
    }

}

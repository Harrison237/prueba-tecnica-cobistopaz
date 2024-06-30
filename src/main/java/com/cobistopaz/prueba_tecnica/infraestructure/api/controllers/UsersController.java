package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.services.IUsersService;

import org.springframework.http.MediaType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "api/users")
public class UsersController {

    @Autowired
    private IUsersService usersService;

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> postMethodName(@RequestBody UserDto nuevoUsuario) {
        List<User> todos = null;

        try {
            todos = this.usersService.buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return todos;
    }

    @GetMapping("{id}")
    public User getMethodName(@PathVariable String id) throws Exception {
        return this.usersService.buscarPorId(id);

    }

}

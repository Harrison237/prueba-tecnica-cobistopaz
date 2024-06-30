package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.cobistopaz.prueba_tecnica.domain.modelview.UserMV;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.services.IUsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(value="api/users")
public class UsersController {

    @Autowired
    IUsersService usersService;

    @PostMapping("create")
    public UserMV postMethodName(@RequestBody UserDto nuevoUsuario) {
        return null;
    }
    
}

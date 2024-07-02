package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cobistopaz.prueba_tecnica.application.services.JwtService;
import com.cobistopaz.prueba_tecnica.application.usecases.IUsersService;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;
import com.google.gson.Gson;

@WebMvcTest(controllers = UsersController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private IUsersService usersService;

    @MockBean
    private JwtService jwtService;

    private User userPrueba;

    @BeforeEach
    void init() {
        this.userPrueba = User
                .builder()
                .id(System.currentTimeMillis() + "")
                .nombreUsuario("Harrison")
                .contrasena("asd123456")
                .roles("1,2,3,4,5")
                .build();
    }

    @Test
    void testGuardarUsuario() throws Exception {
        RegisterUserDto registrar = RegisterUserDto
                .builder()
                .nombreUsuario(userPrueba.getNombreUsuario())
                .contrasena(userPrueba.getContrasena())
                .roles(userPrueba.getRoles())
                .build();

        given(usersService.crearUsuario(registrar)).willReturn(userPrueba);

        ResultActions response = mockMvc.perform(post("/api/users/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(registrar)));

        response.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.mensaje", is("Usuario creado satisfactoriamente")))
            .andExpect(result -> System.out.println(result.getClass()));

    }
}

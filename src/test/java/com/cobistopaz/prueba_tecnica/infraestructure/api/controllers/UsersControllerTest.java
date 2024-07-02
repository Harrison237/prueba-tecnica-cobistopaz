package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.is;

import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UpdateUserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.api.security.IAuthManager;
import org.junit.jupiter.api.DisplayName;
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

import com.cobistopaz.prueba_tecnica.application.usecases.IUsersService;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

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
    private IAuthManager jwtService;

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

    @DisplayName("Tests para guardar un usuario desde el método post del controlador de usuarios")
    @Test
    void testGuardarUsuario() throws Exception {
        RegisterUserDto registrar = RegisterUserDto
                .builder()
                .nombreUsuario(userPrueba.getNombreUsuario())
                .contrasena(userPrueba.getContrasena())
                .roles(userPrueba.getRoles())
                .build();
        userPrueba.setContrasena("");

        given(usersService.crearUsuario(registrar)).willReturn(userPrueba);

        ResultActions response = mockMvc.perform(post("/api/users/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(registrar)));

        response.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.mensaje", is("Usuario creado satisfactoriamente")))
                .andExpect(jsonPath("$.contenido.contrasena", is("")));

    }

    @DisplayName("Tests para buscar un usuario por un id enviado en la petición")
    @Test
    void testBuscarUsuarioPorId() throws Exception {
        userPrueba.setContrasena("");
        given(usersService.buscarPorId(userPrueba.getId())).willReturn(userPrueba);

        ResultActions response = mockMvc.perform(get("/api/users/"+userPrueba.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.mensaje", is("Ok")))
                .andExpect(jsonPath("$.contenido.id", is(userPrueba.getId())))
                .andExpect(jsonPath("$.contenido.contrasena", is("")));
    }

    @DisplayName("Tests para consultar todos los usuarios mediante el controlador")
    @Test
    void testBuscarTodosLosUsuarios() throws Exception {
        List<User> usuarios = Arrays.asList(
                User.builder().id(System.currentTimeMillis()+"").nombreUsuario("Daniel").contrasena("12435").roles("a,b,c").build(),
                User.builder().id(System.currentTimeMillis()+"").nombreUsuario("Juan").contrasena("12435").roles("a,b,c").build(),
                User.builder().id(System.currentTimeMillis()+"").nombreUsuario("Andres").contrasena("12435").roles("a,b,c").build(),
                User.builder().id(System.currentTimeMillis()+"").nombreUsuario("Sandra").contrasena("12435").roles("a,b,c").build()
        );
        given(usersService.buscarTodos()).willReturn(usuarios);
        usuarios.stream().peek(usuario -> usuario.setContrasena("")).toList();

        ResultActions response = mockMvc.perform(get("/api/users/todos"));

        response.andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.mensaje", is("Ok")))
                .andExpect(jsonPath("$.contenido.size()", is(usuarios.size())));
    }

    @DisplayName("Tests para actualizar un usuario mediante el controlador de usuarios")
    @Test
    void testActualizarUsuario() throws Exception {
        userPrueba.setContrasena("");
        UpdateUserDto enviar = UpdateUserDto.builder().nombreUsuario("Cambiado").roles("aaa,bbb,ccc").build();
        User cambiado = User.builder().id(userPrueba.getId()).nombreUsuario(enviar.getNombreUsuario()).contrasena("").roles(enviar.getRoles()).build();

        given(usersService.modificar(userPrueba.getId(), enviar)).willReturn(cambiado);

        ResultActions response = mockMvc.perform(patch("/api/users/actualizar/"+userPrueba.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(enviar)));

        response.andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.mensaje", is("Usuario actualizado exitosamente")))
                .andExpect(jsonPath("$.contenido.id", is(cambiado.getId())))
                .andExpect(jsonPath("$.contenido.nombreUsuario", is(cambiado.getNombreUsuario())));
    }

    @DisplayName("Tests para eliminar un usuario mediante el controlador de usuarios")
    @Test
    void testEliminarUsuario() throws Exception {
        doAnswer(invocation -> {
            userPrueba = null;
            return null;
        }).when(usersService).eliminar(userPrueba.getId(), userPrueba.getContrasena());

        ResultActions response = mockMvc.perform(delete("/api/users/eliminar/"+userPrueba.getId()+"/"+userPrueba.getContrasena())
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.mensaje", is("Usuario eliminado exitosamente")))
                .andExpect(jsonPath("$.contenido", is("")));
    }
}

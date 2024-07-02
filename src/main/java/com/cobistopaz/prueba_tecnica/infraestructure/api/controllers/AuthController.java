package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Controlador de Autenticación", description = "Desde este controlador puede generar nuevos usuarios sin un token de autorización, y generar un token de autorización a partir de las credenciales de un usuario ya registrado.")
@AllArgsConstructor
@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @Operation(
            summary = "Ingresar en el sistema",
            description = "Se encarga de validar las credenciales ingresadas. En caso de que sean correctas, otorgará un token de autorización para utilizar los métodos del Controlador de Usuarios, si no, retornará un mensaje de error."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna este mensaje de error cuando las credenciales otorgadas no son validas, ya sea porque el usuario no existe o porque el usuario y la contraseña no coinciden. Puede generar usuarios sin estar auténticado desde el método \"Registrar usuario\" de este mismo controlador."),
    })
    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp<IAuthResponse>> getToken(@RequestBody AuthUserDto credenciales) throws Exception {
        IAuthResponse respuesta = authService.ingresar(credenciales);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                RespuestaHttpFactory.respuestaExitosa("Se ha autenticado éxitosamente.", HttpStatus.ACCEPTED.value(),
                        respuesta));
    }

    @Operation(
            summary = "Registrar usuario",
            description = "Crea un usuario en la base de datos. La diferencia con el EndPoint de crear usuario del controlador de usuarios, es que este EndPoint no requiere de autorización para crear usuarios, mientras que el otro sí."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna este error cuando el nombre de usuario ingresado ya existe en la base de datos.")
    })
    @PostMapping(value = "registrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp<User>> registrarUsuario(@RequestBody RegisterUserDto nuevo) throws Exception {
        User creado = authService.RegistrarUsuario(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                RespuestaHttpFactory.respuestaExitosa(
                        "Usuario registrado exitosamente. Ahora puede usar sus nuevas credenciales para iniciar sesión. Recuerde que una vez agregado el token de autorización, los métodos del auth-controller quedarán inhabilitados hasta que el token sea removido de la petición.",
                        HttpStatus.CREATED.value(), creado));
    }
}

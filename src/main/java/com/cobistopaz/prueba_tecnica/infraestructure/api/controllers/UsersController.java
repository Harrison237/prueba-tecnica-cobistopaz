package com.cobistopaz.prueba_tecnica.infraestructure.api.controllers;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

import com.cobistopaz.prueba_tecnica.application.usecases.IUsersService;
import com.cobistopaz.prueba_tecnica.domain.web.RespuestaHttp;
import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UpdateUserDto;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Controlador de Usuarios", description = "Controlador para las operaciones de CRUD de usuarios.")
@RestController
@RequestMapping(value = "api/users")
public class UsersController {

        @Autowired
        private IUsersService usersService;

        @Operation(
                summary = "Consulta y devuelve todos los usuarios",
                description = "Se encarga de buscar todos los usuarios en la base de datos y retornarlos mediante el atributo \"contenido\" del objeto estandarizado \"RespuestaHttp\"")
        @ApiResponses({
                @ApiResponse(responseCode = "202", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna esta respuesta cuando el token de autorización ingresado es erróneo. Puede obtener un token válido desde el apartado de Controlador de Autenticación."),
                @ApiResponse(responseCode = "403", content = @Content(schema = @Schema()), description = "Se retorna esta respuesta sin cuerpo cuando no ha agregado un token de autorización. Puede obtener uno desde el apartado de Controlador de Autenticación.")
        })
        @GetMapping(value = "todos", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RespuestaHttp<List<User>>> getTodos() throws Exception {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                                RespuestaHttpFactory.respuestaExitosa(HttpStatus.ACCEPTED.value(), usersService.buscarTodos()));
        }

        @Operation(
                summary = "Consulta un usuario por id",
                description = "Busca un usuario en la base de datos con el ID otorgado por parámetro de ruta. En caso de encontrarlo, lo retorna en el contenido de la respuesta, si no, retorna un mensaje de error de usuario no encontrado."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "202", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna esta respuesta cuando el token de autorización ingresado es erróneo. Puede obtener un token válido desde el apartado de Controlador de Autenticación."),
                @ApiResponse(responseCode = "403", content = @Content(schema = @Schema()), description = "Se retorna esta respuesta sin cuerpo cuando no ha agregado un token de autorización. Puede obtener uno desde el apartado de Controlador de Autenticación."),
                @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
        })
        @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RespuestaHttp<User>> getUsuarioPorId(
                @Parameter(description = "ID del usuario a buscar en la base de datos") @PathVariable String id
        ) throws Exception {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                                RespuestaHttpFactory.respuestaExitosa(HttpStatus.ACCEPTED.value(), usersService.buscarPorId(id)));
        }

        @Operation(
                summary = "Crea un usuario",
                description = "Crea un usuario en la base de datos. Puede retornar una respuesta de error si el nombre de usuario enviado ya se encuentra registrado en el sistema."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna esta respuesta cuando el token de autorización ingresado es erróneo. Puede obtener un token válido desde el apartado de Controlador de Autenticación."),
                @ApiResponse(responseCode = "403", content = @Content(schema = @Schema()), description = "Se retorna esta respuesta sin cuerpo cuando no ha agregado un token de autorización. Puede obtener uno desde el apartado de Controlador de Autenticación."),
                @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna este error cuando el nombre de usuario ingresado ya existe en la base de datos.")
        })
        @PostMapping(value = "crear", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RespuestaHttp<User>> crearNuevoUsuario(@Valid @RequestBody RegisterUserDto nuevoUsuario) throws Exception {
                return ResponseEntity.status(HttpStatus.CREATED).body(
                                RespuestaHttpFactory.respuestaExitosa(
                                                "Usuario creado satisfactoriamente", HttpStatus.CREATED.value(), usersService.crearUsuario(nuevoUsuario)));
        }

        @Operation(
                summary = "Actualiza un usuario",
                description = "Actualiza un usuario que ya se encuentra en la base de datos. Puede retornar distintas respuestas de error, como que no existe un usuario con el id que ha otorgado, o errores relacionados con los campos de contraseña actual y contraseña nueva."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna esta respuesta cuando el token de autorización ingresado es erróneo. Puede obtener un token válido desde el apartado de Controlador de Autenticación."),
                @ApiResponse(responseCode = "403", content = @Content(schema = @Schema()), description = "Se retorna esta respuesta sin cuerpo cuando no ha agregado un token de autorización. Puede obtener uno desde el apartado de Controlador de Autenticación."),
                @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna este error cuando no se encuentra un usuario con el id ingresado."),
                @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna este error cuando no ha enviado ningún cuerpo en la petición para la actualización del usuario."),
                @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna este error cuando existen conflictos con los campos de contraseña. Estos pueden ser que ingresó el campo de contraseña nueva pero no el de contraseña actual, que ambos campos de contraseña son iguales, o que el valor del campo de contraseña actual no coincida con el valor guardado en la base de datos.")
        })
        @PatchMapping(value = "actualizar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RespuestaHttp<User>> actualizarUsuario(
                @Valid @RequestBody UpdateUserDto usuario,
                @Parameter(description = "ID del usuario a actualizar en la base de datos.") @PathVariable String id
        ) throws Exception {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                                RespuestaHttpFactory.respuestaExitosa(
                                                "Usuario actualizado exitosamente", HttpStatus.ACCEPTED.value(), usersService.modificar(id, usuario)));
        }

        @Operation(
                summary = "Elimina un usuario",
                description = "Se encarga de eliminar un usuario existente de la base de datos. No retorna nada en el contenido del cuerpo. Puede retornar distintas respuestas de error, como que no existe un usuario con el id que ha otorgado, o que la contraseña otorgada no coincide con la contraseña guardada en la base de datos."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna esta respuesta cuando el token de autorización ingresado es erróneo. Puede obtener un token válido desde el apartado de Controlador de Autenticación."),
                @ApiResponse(responseCode = "403", content = @Content(schema = @Schema()), description = "Se retorna esta respuesta sin cuerpo cuando no ha agregado un token de autorización. Puede obtener uno desde el apartado de Controlador de Autenticación."),
                @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna este error cuando no se encuentra un usuario con el id ingresado."),
                @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = RespuestaHttp.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Se retorna este error cuando la contraseña ingresada no coincide con la guardada en la base de datos.")
        })
        @DeleteMapping(value = "eliminar/{id}/{contrasena}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RespuestaHttp<String>> eliminarUsuario(
                @Parameter(description = "ID del usuario a eliminar en la base de datos") @PathVariable String id,
                @Parameter(description = "Contraseña del usuario a eliminar. Validación extra agregada por conveniencia") @PathVariable String contrasena
        ) throws Exception {
                usersService.eliminar(id, contrasena);

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                                RespuestaHttpFactory.respuestaExitosa("Usuario eliminado exitosamente", HttpStatus.ACCEPTED.value(), ""));
        }

}

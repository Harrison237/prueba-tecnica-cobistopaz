package com.cobistopaz.prueba_tecnica.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cobistopaz.prueba_tecnica.application.ports.UsersPort;
import com.cobistopaz.prueba_tecnica.domain.exceptions.ContrasenaException;
import com.cobistopaz.prueba_tecnica.domain.exceptions.PeticionVaciaException;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.UpdateUserDto;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UsersServiceImplTests {

    @Mock
    private UsersPort usersRepository;

    @Mock
    private PasswordEncoder codificador;

    @InjectMocks
    private UsersServiceImpl usersService;

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

    @DisplayName("Tests para guardar un usuario desde el servicio de usuarios")
    @Test
    public void testGuardarUsuario() throws Exception {
        given(codificador.encode(userPrueba.getContrasena())).willReturn("contrasenaEncriptada");
        given(usersRepository.guardar(Mockito.any(User.class))).willReturn(userPrueba);

        RegisterUserDto guardar = RegisterUserDto
                .builder()
                .nombreUsuario(userPrueba.getNombreUsuario())
                .contrasena(userPrueba.getContrasena())
                .roles(userPrueba.getRoles())
                .build();

        User usuarioGuardado = usersService.crearUsuario(guardar);

        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getContrasena()).isEqualTo("");
    }

    @DisplayName("Tests para método de buscar usuario por ID desde servicio")
    @Test
    public void testBuscarUsuario() throws Exception {
        given(usersRepository.consultarPorId("12345")).willThrow(NoSuchElementException.class);
        given(usersRepository.consultarPorId(userPrueba.getId())).willReturn(userPrueba);

        User buscar = usersService.buscarPorId(userPrueba.getId());

        assertThat(buscar).isNotNull();
        assertThat(buscar.getId()).isEqualTo(userPrueba.getId());
        assertThat(buscar.getContrasena()).isEqualTo("");
    }

    @DisplayName("Test para consultar los usuarios desde el servicio de usuarios")
    @Test
    public void testConsultarTodosLosUsuarios() throws Exception {
        User user2 = User
                .builder()
                .id(System.currentTimeMillis() + "")
                .nombreUsuario("Harrison2")
                .contrasena("asd1234562")
                .roles("1,2,3,4,5")
                .build();

        User user3 = User
                .builder()
                .id(System.currentTimeMillis() + "")
                .nombreUsuario("Harrison3")
                .contrasena("asd1234562")
                .roles("1,2,3,4,5")
                .build();
        List<User> usuarios = Arrays.asList(userPrueba, user2, user3);
        given(usersRepository.consultarTodos()).willReturn(usuarios);

        List<User> encontrados = usersService.buscarTodos();

        assertThat(encontrados).isNotNull();
        assertThat(encontrados).isNotEmpty();
        assertThat(encontrados).allMatch(user -> user.getContrasena().equals(""));
    }

    @DisplayName("Test para verificar las comprobaciones de actualización de un usuario")
    @Test
    public void testActualizarUsuario() throws Exception {
        UpdateUserDto vacio = new UpdateUserDto();
        UpdateUserDto sinContrasenaActual = UpdateUserDto.builder().contrasenaNueva("aaaaaaa").build();
        UpdateUserDto contrasenasIguales = UpdateUserDto.builder().contrasenaActual("aaa").contrasenaNueva("aaa")
                .build();
        UpdateUserDto contrasenaActualIncorrecta = UpdateUserDto.builder().contrasenaActual("12345")
                .contrasenaNueva("aaaa").build();
        UpdateUserDto pruebaActualizar = UpdateUserDto.builder()
                .nombreUsuario("NuevoNombre")
                .contrasenaActual("asd123456").contrasenaNueva("asd1234")
                .roles("admin,admin").build();
        User actualizado = User.builder().id(userPrueba.getId()).nombreUsuario(pruebaActualizar.getNombreUsuario())
                .contrasena("").roles(pruebaActualizar.getRoles()).build();

        given(usersRepository.consultarPorId(userPrueba.getId())).willReturn(userPrueba);
        given(codificador.matches(contrasenaActualIncorrecta.getContrasenaActual(), userPrueba.getContrasena()))
                .willReturn(false);
        given(codificador.matches(pruebaActualizar.getContrasenaActual(), userPrueba.getContrasena())).willReturn(true);
        given(codificador.encode(pruebaActualizar.getContrasenaNueva())).willReturn("NuevaContrasenaEncriptada");
        given(usersRepository.modificar(userPrueba.getId(), userPrueba)).willReturn(actualizado);

        User completado = usersService.modificar(userPrueba.getId(), pruebaActualizar);

        assertThrows(PeticionVaciaException.class, () -> usersService.modificar(userPrueba.getId(), vacio));
        assertThrows(ContrasenaException.class, () -> usersService.modificar(userPrueba.getId(), sinContrasenaActual));
        assertThrows(ContrasenaException.class, () -> usersService.modificar(userPrueba.getId(), contrasenasIguales));
        assertThrows(ContrasenaException.class,
                () -> usersService.modificar(userPrueba.getId(), contrasenaActualIncorrecta));
        assertThat(completado).isNotNull();
        assertThat(completado.getContrasena()).isEqualTo("");
        assertThat(completado.getNombreUsuario()).isEqualTo(pruebaActualizar.getNombreUsuario());
        assertThat(completado.getRoles()).isEqualTo(pruebaActualizar.getRoles());
    }

    @DisplayName("Test para casos en que se quiere eliminar un usuario")
    @Test
    public void testEliminarUsuario() throws Exception {
        given(usersRepository.consultarPorId(userPrueba.getId())).willReturn(userPrueba);
        given(codificador.matches("asd123456", userPrueba.getContrasena())).willReturn(true);
        given(codificador.matches("aaaaa", userPrueba.getContrasena())).willReturn(false);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                userPrueba = null;
                return null;
            }
        }).when(usersRepository).eliminar(userPrueba);

        assertThrows(ContrasenaException.class, () -> usersService.eliminar("12345", null));
        assertThrows(ContrasenaException.class, () -> usersService.eliminar(userPrueba.getId(), "aaaaa"));

        usersService.eliminar(userPrueba.getId(), "asd123456");

        assertThat(userPrueba).isNull();
    }

    @DisplayName("Test para comprobar el mapeo de clase Dto a User")
    @Test
    public void probarMapeos() throws Exception {
        RegisterUserDto registrar = RegisterUserDto
                .builder()
                .nombreUsuario(userPrueba.getNombreUsuario())
                .contrasena(userPrueba.getContrasena())
                .roles(userPrueba.getRoles())
                .build();

        User convertido = usersService.desdeDtoAUser(registrar);

        assertThat(convertido).isInstanceOf(User.class);
        assertThat(convertido.getId()).isNull();
        assertThat(convertido.getNombreUsuario()).isEqualTo(registrar.getNombreUsuario()).isEqualTo(userPrueba.getNombreUsuario());
    }
}

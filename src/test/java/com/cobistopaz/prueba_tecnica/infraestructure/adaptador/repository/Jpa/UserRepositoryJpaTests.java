package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.entity.UserEntity;

@DataJpaTest
public class UserRepositoryJpaTests {
    @Autowired
    private UserRepositoryJpa repository;

    private UserEntity userPrueba;

    @BeforeEach
    void init() {
        this.userPrueba = UserEntity
                .builder()
                .id(System.currentTimeMillis() + "")
                .nombreUsuario("Harrison")
                .contrasena("asd123456")
                .roles("1,2,3,4,5")
                .build();
    }

    @DisplayName("Test de guardar un usuario con repositorio.")
    @Test
    public void testGuardarUsuario() {
        UserEntity guardado = repository.save(userPrueba);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isInstanceOf(String.class);
        assertThat(guardado.getId()).isNotEqualTo("");
        assertThat(guardado.getRoles().split(",")).isNotEmpty();
    }

    @DisplayName("Test de consultar todos los usuarios")
    @Test
    public void testConsultarUsuarios() {
        UserEntity usuario2 = UserEntity
                .builder()
                .id(System.currentTimeMillis() + "1")
                .nombreUsuario("Daniel")
                .contrasena("123456")
                .roles(userPrueba.getRoles())
                .build();

        repository.save(userPrueba);
        repository.save(usuario2);

        List<UserEntity> todos = repository.findAll();

        assertThat(todos).isNotEmpty();
        assertThat(todos.size()).isEqualTo(2);
    }

    @DisplayName("Test de encontrar usuario por Id")
    @Test
    public void testEncontrarUsuarioId() {
        String id = userPrueba.getId();
        repository.save(userPrueba);

        Optional<UserEntity> encontrado = repository.findById(id);

        assertThat(encontrado.isPresent()).isTrue();
        assertThat(encontrado.get()).isNotNull();
        assertThat(encontrado.get().getId()).isEqualTo(userPrueba.getId());
    }

    @DisplayName("Test de eliminar usuario")
    @Test
    public void testEliminarUsuario() {
        String id = userPrueba.getId();
        repository.save(userPrueba);

        repository.delete(userPrueba);
        Optional<UserEntity> buscar = repository.findById(id);

        assertThat(buscar.isPresent()).isFalse();
        assertThrows(RuntimeException.class, () -> {
            buscar.get();
        });
    }

    @DisplayName("Test de modificar usuario")
    @Test
    public void modificarUsuario() {
        String id = userPrueba.getId();
        repository.save(userPrueba);

        String nuevaContrasena = "567890";
        userPrueba.setContrasena(nuevaContrasena);
        UserEntity guardado = repository.save(userPrueba);

        assertThat(guardado.getId()).isEqualTo(id);
        assertThat(guardado.getContrasena()).isEqualTo(nuevaContrasena);
    }
}

package com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.Jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.Jpa.entity.UserEntity;

public interface UserRepositoryJpa extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findOneByNombreUsuario(String nombreUsuario);
}

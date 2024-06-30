package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
@DynamicUpdate
public class UserEntity {
    @Id @Column(name = "id")
    private String id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "roles")
    private String roles;
}

package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.entity.UserEntity;

@Mapper
public interface IUserMapperJpa {

    IUserMapperJpa mapper = Mappers.getMapper(IUserMapperJpa.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombreUsuario", target = "nombreUsuario")
    @Mapping(source = "contrasena", target = "contrasena")
    @Mapping(source = "roles", target = "roles")
    UserEntity desdeDomainAEntity(User domain);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombreUsuario", target = "nombreUsuario")
    @Mapping(source = "contrasena", target = "contrasena")
    @Mapping(source = "roles", target = "roles")
    User desdeEntityADomain(UserEntity entity);
}

package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.dto.RegisterUserDto;

@Mapper
public interface IUserMapper {
    IUserMapper mapper = Mappers.getMapper(IUserMapper.class);
    
    @Mapping(source = "nombreUsuario", target = "nombreUsuario")
    @Mapping(source = "contrasena", target = "contrasena")
    @Mapping(source = "roles", target = "roles")
    User desdeDtoADomain(RegisterUserDto dto);

    @Mapping(source = "nombreUsuario", target = "nombreUsuario")
    @Mapping(source = "contrasena", target = "contrasena")
    @Mapping(source = "roles", target = "roles")
    RegisterUserDto desdeDomainADto(User user);
}

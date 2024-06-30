package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.mappers;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.entity.UserEntity;

public interface IUserMapper {
    //Este método se encarga de convertir el objeto de clase User del paquete domain a un objeto de clase UserEntity
    UserEntity desdeDomainAEntity(User domain);

}

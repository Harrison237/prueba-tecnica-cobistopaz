package com.cobistopaz.prueba_tecnica.infraestructure.adaptador;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cobistopaz.prueba_tecnica.application.ports.UsersPort;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.UserRepositoryJpa;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.entity.UserEntity;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.mappers.IUserMapperJpa;

@Component
public class UserPersistenceAdapter implements UsersPort {

    @Autowired
    private UserRepositoryJpa usersManager;

    @Override
    public User guardar(User user) throws Exception {
        UserEntity entity = IUserMapperJpa.mapper.desdeDomainAEntity(user);

        return IUserMapperJpa.mapper.desdeEntityADomain(usersManager.save(entity));
    }

    @Override
    public User consultarPorId(String id) throws Exception {
        return IUserMapperJpa.mapper.desdeEntityADomain(usersManager.findById(id).get());
    }

    @Override
    public List<User> consultarTodos() throws Exception {
        List<UserEntity> users = usersManager.findAll();

        return users.stream().map(
                user -> IUserMapperJpa.mapper.desdeEntityADomain(user)).collect(Collectors.toList());
    }

    @Override
    public User modificar(String id, User user) throws Exception {
        UserEntity entity = IUserMapperJpa.mapper.desdeDomainAEntity(user);

        return IUserMapperJpa.mapper.desdeEntityADomain(usersManager.save(entity));
    }

    @Override
    public void eliminar(User user) throws Exception {
        UserEntity entity = IUserMapperJpa.mapper.desdeDomainAEntity(user);

        usersManager.delete(entity);
    }

    @Override
    public User consultarPorNombreUsuario(String nombreUsuario) throws NoSuchElementException {
        return IUserMapperJpa.mapper.desdeEntityADomain(usersManager.findOneByNombreUsuario(nombreUsuario).get());
    }

}

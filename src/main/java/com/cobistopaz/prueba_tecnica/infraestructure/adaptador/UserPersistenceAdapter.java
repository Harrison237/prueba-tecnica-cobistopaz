package com.cobistopaz.prueba_tecnica.infraestructure.adaptador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cobistopaz.prueba_tecnica.application.ports.UsersPort;
import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.UserRepositoryJpa;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.entity.UserEntity;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.mappers.IUserMapperJpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class UserPersistenceAdapter implements UsersPort {

    @Autowired
    private UserRepositoryJpa usersManager;

    @Override
    public User guardar(User user) {
        UserEntity entity = IUserMapperJpa.mapper.desdeDomainAEntity(user);

        return IUserMapperJpa.mapper.desdeEntityADomain(usersManager.save(entity));
    }

    @Override
    public User consultarPorId(String id) {
        return IUserMapperJpa.mapper.desdeEntityADomain(usersManager.findById(id).get());
    }

    @Override
    public List<User> consultarTodos() {
        List<UserEntity> users = usersManager.findAll();

        return users.stream().map(
                user -> IUserMapperJpa.mapper.desdeEntityADomain(user)).collect(Collectors.toList());
    }

    @Override
    public User modificar(String id, User user) {
        UserEntity entity = IUserMapperJpa.mapper.desdeDomainAEntity(user);

        return IUserMapperJpa.mapper.desdeEntityADomain(usersManager.save(entity));
    }

    @Override
    public void eliminar(User user) {
        UserEntity entity = IUserMapperJpa.mapper.desdeDomainAEntity(user);

        usersManager.delete(entity);
    }

}

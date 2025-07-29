package com.cobistopaz.pruebatecnica.infraestructure.adaptador;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.cobistopaz.pruebatecnica.application.ports.UsersPort;
import com.cobistopaz.pruebatecnica.domain.model.User;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.Jpa.UserRepositoryJpa;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.Jpa.entity.UserEntity;
import com.cobistopaz.pruebatecnica.infraestructure.adaptador.repository.Jpa.mappers.IUserMapperJpa;

@Component
@AllArgsConstructor
public class UserPersistenceAdapter implements UsersPort {

    private UserRepositoryJpa usersManager;

    @Override
    public User guardar(User user) throws Exception {
        UserEntity entity = IUserMapperJpa.mapper.desdeDomainAEntity(user);

        return IUserMapperJpa.mapper.desdeEntityADomain(usersManager.save(entity));
    }

    @Override
    public User consultarPorId(String id) throws Exception {
        Optional<UserEntity> finded = usersManager.findById(id);

        return finded.map(IUserMapperJpa.mapper::desdeEntityADomain).orElse(null);
    }

    @Override
    public List<User> consultarTodos() throws Exception {
        List<UserEntity> users = usersManager.findAll();

        return users.stream().map(
                IUserMapperJpa.mapper::desdeEntityADomain).toList();
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
        Optional<UserEntity> finded = usersManager.findOneByNombreUsuario(nombreUsuario);

        return finded.map(IUserMapperJpa.mapper::desdeEntityADomain).orElse(null);
    }

}

package com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cobistopaz.prueba_tecnica.domain.model.User;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.IUserRepository;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.entity.UserEntity;
import com.cobistopaz.prueba_tecnica.infraestructure.adaptador.repository.Jpa.mappers.IUserMapperJpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class UserRepositoryJpaMysql implements IUserRepository {

    @PersistenceContext
    private EntityManager usersManager;

    @Override
    public User guardar(User user) {
        UserEntity entity = IUserMapperJpa.mapper.desdeDomainAEntity(user);

        return IUserMapperJpa.mapper.desdeEntityADomain(usersManager.merge(entity));
    }

    @Override
    public User consultarPorId(String id) {
        String query = "FROM UserEntity WHERE id = :id";
        return IUserMapperJpa.mapper.desdeEntityADomain((UserEntity) usersManager.createQuery(query)
                .setParameter("id", id)
                .getSingleResult());
    }

    @Override
    public List<User> consultarTodos() {
        String query = "FROM UserEntity";
        @SuppressWarnings("unchecked")
        List<UserEntity> users = usersManager.createQuery(query).getResultList();

        return users.stream().map(
            user -> IUserMapperJpa.mapper.desdeEntityADomain(user)).collect(Collectors.toList());
    }

    @Override
    public User modificar(String id, User user) {
        throw new UnsupportedOperationException("Unimplemented method 'modificar'");
    }

    @Override
    public void eliminar(User user) {
        usersManager.remove(IUserMapperJpa.mapper.desdeDomainAEntity(user));
    }

}

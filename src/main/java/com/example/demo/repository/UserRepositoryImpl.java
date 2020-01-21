package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addNewUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List getAllUsers() {
        return entityManager.createQuery("Select t from " + User.class + " t").getResultList();
    }

    @Override
    public User getUserByID(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void removeUser(Long id) {
        entityManager.remove(getUserByID(id));
    }

    @Override
    public void editExistingUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserByName(String name) {
        return entityManager.createQuery("Select u from " + User.class.getSimpleName() + " u where u.name = :name", User.class).setParameter("name", name).getSingleResult();
    }
}

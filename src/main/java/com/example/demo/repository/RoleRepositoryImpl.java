package com.example.demo.repository;
import com.example.demo.model.Role;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role findById(long id) {
        return  entityManager.find(Role.class, id);
    }

    @Override
    public Role findByName(String name) {
        return entityManager.createQuery("Select r from Role r where r.role = :name", Role.class).setParameter("name", name).getSingleResult();
    }
}

package com.studentmanager.domain.db;

import com.studentmanager.domain.models.Persistable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Stream;

/**
 * An database way of storing the application data.
 * Contains implementation for the basic CRUD operations.
 */
public class JpaDataStore<T extends Persistable<ID>, ID>
        implements DataStore<T, ID> {
    private EntityManager em;
    private Class<T> entityClass;

    private JpaDataStore(Class<T> entityClass) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("student_manager");
        this.em = emf.createEntityManager();
        this.entityClass = entityClass;
    }

    /**
     * A static factory method, used for instantiation.
     *
     * @param entityClass - The class-type of the persisted entities
     * @param <T>         - The type of the persisted entities
     * @param <ID>        - The type of persisted entities' id
     * @return the newly created JpaDataStore
     */
    public static <T extends Persistable<ID>, ID>
    JpaDataStore<T, ID> create(Class<T> entityClass) {
        return new JpaDataStore<>(entityClass);
    }

    @Override
    public T getById(ID id) {
        em.getTransaction().begin();
        T t = em.find(entityClass, id);
        em.getTransaction().commit();
        return t;
    }

    @Override
    public Stream<T> getAll() {
        em.getTransaction().begin();
        List<T> resultList = em
                .createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                .getResultList();
        em.getTransaction().commit();
        return resultList.stream();
    }

    @Override
    public <S extends T> S save(S entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    @Override
    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }
}

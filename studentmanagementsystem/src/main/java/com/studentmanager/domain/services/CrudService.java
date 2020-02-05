package com.studentmanager.domain.services;

import com.studentmanager.domain.exceptions.io.EmptyStorageException;
import com.studentmanager.domain.exceptions.io.EntityNotFoundException;
import com.studentmanager.domain.repositories.Repository;

import java.util.stream.Stream;

/**
 * This abstract class is the base for all services,
 * which use repositories for performing CRUD operations
 * over persistable entities.
 *
 * @param <T>  - The type of the persisted entities
 * @param <ID> - The type of persisted entities' id
 */
public abstract class CrudService<T, ID> {
    protected Repository<T, ID> repository;

    protected CrudService() {
        // Default constructor
    }

    protected CrudService(Repository<T, ID> repository) {
        this.repository = repository;
    }

    public T save(T t) {
        return this.repository.save(t);
    }

    public T findById(ID id) throws EntityNotFoundException {
        return this.repository.findById(id);
    }

    public Stream<T> findAll() throws EmptyStorageException {
        return this.repository.findAll();
    }

    public T update(T t) {
        return this.repository.update(t);
    }

    public void delete(T t) {
        this.repository.delete(t);
    }
}

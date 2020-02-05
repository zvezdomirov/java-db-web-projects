package com.studentmanager.domain.db;

import com.studentmanager.domain.models.Persistable;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * An in-memory way of storing the application data.
 * Contains implementation for the basic CRUD operations.
 */
public class CollectionDataStore<T extends Persistable<ID>, ID>
        implements DataStore<T, ID> {
    private Map<ID, T> data;

    private CollectionDataStore() {
        this.data = new HashMap<>();
    }

    /**
     * A static factory method, used for instantiation.
     *
     * @param <T>  - The type of the persisted entities
     * @param <ID> - The type of persisted entities' id
     * @return the newly created CollectionDataStore
     */
    public static <T extends Persistable<ID>, ID>
    CollectionDataStore<T, ID> create() {
        return new CollectionDataStore<>();
    }

    @Override
    public T getById(ID id) {
        return this.data.get(id);
    }

    @Override
    public Stream<T> getAll() {
        return data.values().stream();
    }

    @Override
    public <S extends T> S save(S entity) {
        this.data.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        this.data.remove(entity.getId());
    }
}

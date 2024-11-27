package com.yuri.ubots_test.repository;

import com.yuri.ubots_test.model.Entity;

import java.util.*;

public abstract class AbstractRepository<ID, T extends Entity> {

    protected final Set<T> set;

    public AbstractRepository() {
        set = new HashSet<>();
    }

    public void save(T obj) {
        set.add(obj);
    }


    public Optional<T> getById(ID id) {
        return set.stream().filter(obj -> obj.getId().equals(id)).findFirst();
    }

    public List<T> findAll() {
        return set.stream().toList();
    }

    public boolean update(T obj) {
        var oldObject = getById((ID) obj.getId());
        if (oldObject.isEmpty()) {
            return false;
        }

        deleteById((ID) obj.getId());

        set.add(obj);

        return true;
    }

    public boolean deleteById(ID id) {
        return set.removeIf(obj -> obj.getId().equals(id));
    }

    public boolean deleteAll() {
        return set.removeIf(obj -> true);
    }
}
